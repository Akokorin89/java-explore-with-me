package com.example.ewmmainservice.event.service;

import com.example.ewmmainservice.category.model.Category;
import com.example.ewmmainservice.event.dto.EventFullDto;
import com.example.ewmmainservice.event.dto.EventState;
import com.example.ewmmainservice.event.dto.EventUpdateDto;
import com.example.ewmmainservice.event.dto.NewEventDto;
import com.example.ewmmainservice.event.mapper.EventMapper;
import com.example.ewmmainservice.event.model.Event;
import com.example.ewmmainservice.event.repository.EventRepository;
import com.example.ewmmainservice.exception.ForbiddenException;
import com.example.ewmmainservice.exception.NotFoundException;
import com.example.ewmmainservice.request.dto.RequestDto;
import com.example.ewmmainservice.request.dto.RequestStatus;
import com.example.ewmmainservice.request.mapper.RequestMapper;
import com.example.ewmmainservice.request.model.Request;
import com.example.ewmmainservice.request.repository.RequestRepository;
import com.example.ewmmainservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.ewmmainservice.event.dto.EventState.CANCELED;

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
        repository.cancelEvent(CANCELED, eventId);
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
