package ru.practicum.ewmmainservice.participationRequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.event.dto.EventReqStatusUpdateReqDto;
import ru.practicum.ewmmainservice.event.dto.RequestStatusUpdateResponse;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.event.model.State;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.ConflictException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.participationRequest.model.ParticipationRequest;
import ru.practicum.ewmmainservice.participationRequest.model.StatusRequest;
import ru.practicum.ewmmainservice.participationRequest.repository.ParticipationRequestRepository;
import ru.practicum.ewmmainservice.user.model.User;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewmmainservice.participationRequest.mapper.ParticipationRequestMapper.toParticipationRequestDto;
import static ru.practicum.ewmmainservice.participationRequest.mapper.ParticipationRequestMapper.toParticipationRequestsDto;
import static ru.practicum.ewmmainservice.participationRequest.model.StatusRequest.CONFIRMED;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public ParticipationRequestDto saveParticipationRequest(Long userId, Long eventId) {
        if (requestRepository.findByEventIdAndRequesterId(eventId, userId) != null) {
            throw new ConflictException("ParticipationRequestService: Нельзя добавить повторный запрос.");
        }
        User user = getUser(userId);
        Event event = getEvent(eventId);

        if (Objects.equals(userId, event.getInitiator().getId())) {
            throw new ConflictException("ParticipationRequestService: Нельзя добавить запрос от инициализатора события.");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("ParticipationRequestService: Нельзя добавить запрос на не опубликованное событие.");
        }
        if (event.getParticipantLimit() <= requestRepository
                .countParticipationByEventIdAndStatus(eventId, CONFIRMED)) {
            throw new ConflictException("ParticipationRequestService: Лимит участников уже заполнен.");
        }

        ParticipationRequest request = new ParticipationRequest();
        request.setCreated(LocalDateTime.now());
        request.setRequester(user);
        request.setEvent(event);

        if (Boolean.TRUE.equals(event.getRequestModeration())) {
            request.setStatus(StatusRequest.PENDING);
        } else {
            request.setStatus(StatusRequest.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        log.info("ParticipationRequestService: Запрос на участие сохранен.");
        return toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getParticipationRequests(Long userId) {
        log.info("ParticipationRequestService: Получена информация о заявках пользователя.");
        return toParticipationRequestsDto(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getParticipationRequests(Long eventId, Long userId) {
        getUser(userId);
        getEvent(eventId);
        log.info("ParticipationRequestService: Получена информация о запросах на участие в событии пользователя.");
        return toParticipationRequestsDto(requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId));
    }

    @Override
    public RequestStatusUpdateResponse updateParticipationRequest(Long userId, Long eventId, EventReqStatusUpdateReqDto updateReqDto) {
        getUser(userId);
        Event event = getEvent(eventId);

        List<Long> ids = updateReqDto.getRequestIds();
        StatusRequest state = updateReqDto.getStatus();

        List<ParticipationRequestDto> confirmedList = new ArrayList<>();
        List<ParticipationRequestDto> rejectedList = new ArrayList<>();

        for (Long id : ids) {
            if (event.getParticipantLimit() == 0) {
                throw new ConflictException("ParticipationRequestService: Лимит участников уже заполнен.");
            }

            ParticipationRequest request = requestRepository.findByIdAndEvent_Id(id, eventId).orElseThrow(() ->
                    new NotFoundException("ParticipationRequestService: Запрос не найден."));

            if (!request.getStatus().equals(StatusRequest.PENDING)) {
                throw new ConflictException("ParticipationRequestService: Нельзя подтвердить запрос.");
            }
            if (state.equals(StatusRequest.CONFIRMED)) {
                request.setStatus(StatusRequest.CONFIRMED);
                confirmedList.add(toParticipationRequestDto(requestRepository.save(request)));
                event.setParticipantLimit(event.getParticipantLimit() - 1);
            } else {
                request.setStatus(StatusRequest.REJECTED);
                rejectedList.add(toParticipationRequestDto(requestRepository.save(request)));
            }
        }
        eventRepository.save(event);

        log.info("ParticipationRequestService: Статус заявки изменен.");
        return new RequestStatusUpdateResponse(confirmedList, rejectedList);
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        ParticipationRequest request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new ConflictException("ParticipationRequestService: Только организатор может отменить запрос."));
        request.setStatus(StatusRequest.CANCELED);
        log.info("ParticipationRequestService: Запрос отменен.");
        return toParticipationRequestDto(requestRepository.save(request));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("ParticipationRequestService: Неверный ID пользователя."));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("ParticipationRequestService: Неверный ID события."));
    }
}
