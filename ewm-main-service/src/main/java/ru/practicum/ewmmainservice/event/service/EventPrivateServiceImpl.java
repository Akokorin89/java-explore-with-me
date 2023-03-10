package ru.practicum.ewmmainservice.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventState;
import ru.practicum.ewmmainservice.event.dto.EventUpdateDto;
import ru.practicum.ewmmainservice.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.event.mapper.EventMapper;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.ForbiddenException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.request.dto.RequestDto;
import ru.practicum.ewmmainservice.request.dto.RequestStatus;
import ru.practicum.ewmmainservice.request.mapper.RequestMapper;
import ru.practicum.ewmmainservice.request.model.Request;
import ru.practicum.ewmmainservice.request.repository.RequestRepository;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPrivateServiceImpl implements EventPrivateService {
    private final EventRepository repository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventMapper mapper;
    private final RequestMapper requestMapper;

    @Override
    public List<EventFullDto> findEventByInitiatorId(Long userId, int from, int size) {
        findUserById(userId);
        Pageable pageable = PageRequest.of(from / size, size);
        return repository.findAllByInitiatorId(userId, pageable)
                .stream()
                .map(mapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto update(EventUpdateDto eventUpdateDto, Long userId) {
        Event event = findEventById(eventUpdateDto.getEventId());
        isInitiator(event, userId);
        if (eventUpdateDto.getTitle() != null) event.setTitle(eventUpdateDto.getTitle());
        if (eventUpdateDto.getAnnotation() != null) event.setAnnotation(eventUpdateDto.getAnnotation());
        if (eventUpdateDto.getDescription() != null) event.setDescription(eventUpdateDto.getDescription());
        if (eventUpdateDto.getEventDate() != null) event.setEventDate(eventUpdateDto.getEventDate());
        if (eventUpdateDto.getPaid() != null) event.setPaid(eventUpdateDto.getPaid());
        if (eventUpdateDto.getCategory() != null) {
            event.setCategory(new Category(eventUpdateDto.getCategory(), null));
        }
        if (eventUpdateDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        event.setState(EventState.PENDING);
        Event updated = repository.save(event);
        log.info("EventPrivateService: Обновлено событие с id={}.", updated.getId());
        return mapper.toFullDto(updated);
    }

    @Transactional
    @Override
    public EventFullDto save(NewEventDto newEventDto, Long userId) {
        Event event = mapper.toModel(newEventDto, userId);
        Event saved = repository.save(event);
        log.info("EventPrivateService: Сохранено событие={}.", saved);
        return mapper.toFullDto(saved);
    }

    @Override
    public EventFullDto findEventInfoByInitiator(Long userId, Long eventId) {
        Event event = findEventById(eventId);
        isInitiator(event, userId);
        return mapper.toFullDto(event);
    }

    @Transactional
    @Override
    public EventFullDto cancel(Long userId, Long eventId) {
        EventFullDto eventFullDto = findEventInfoByInitiator(userId, eventId);
        repository.cancelEvent(EventState.CANCELED, eventId);
        eventFullDto.setState("CANCELED");
        log.info("EventPrivateService: Отменено событие с id={}.", eventFullDto.getId());
        return eventFullDto;
    }

    @Override
    public List<RequestDto> getAllRequestsByEventId(Long userId, Long eventId) {
        findEventInfoByInitiator(userId, eventId);
        return requestRepository.findAllByEventId(eventId)
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestDto changeRequestStatus(Long userId, Long eventId, Long requestId, boolean isApproved) {
        EventFullDto event = findEventInfoByInitiator(userId, eventId);
        if (event.getConfirmedRequests() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ForbiddenException("EventPrivateService: Превышен лимит заявок на участие в событии с id=" +
                    eventId);
        }
        Request request = findRequestById(requestId);
        if (isApproved) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.REJECTED);
        }
        Request changed = requestRepository.save(request);
        log.info("EventPrivateService: Изменен статус заявки №{}, статус={} на участие в событии с id={}.",
                requestId, changed.getStatus(), changed.getId());
        if (event.getConfirmedRequests() == event.getParticipantLimit() - 1) {
            requestRepository.rejectPendingRequests(eventId);
        }
        return requestMapper.toDto(changed);
    }

    @Override
    public Event findEventById(Long eventId) {
        return repository.findById(eventId)
                .orElseThrow(() ->
                        new NotFoundException("EventPrivateService: Не найдено событие с id=" + eventId));
    }


    private Request findRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() ->
                        new NotFoundException("EventPrivateService: Не найден запрос на участие в событии с id=" +
                                requestId));
    }


    private void findUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("EventPrivateService: Не найден пользователь с id=" + userId));
    }

    private void isInitiator(Event event, Long userId) {
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ForbiddenException("EventPrivateService: Попытка не инициатором внести изменения или получить " +
                    "информацию о событии с id=" + event.getId());
        }
    }
}
