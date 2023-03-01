package com.example.ewmmainservice.event.service;

import com.example.ewmmainservice.event.dto.EventFullDto;
import com.example.ewmmainservice.event.dto.EventShortDto;
import com.example.ewmmainservice.event.dto.EventSort;
import com.example.ewmmainservice.event.dto.EventState;
import com.example.ewmmainservice.event.mapper.EventMapper;
import com.example.ewmmainservice.event.model.Event;
import com.example.ewmmainservice.event.repository.EventRepository;
import com.example.ewmmainservice.exception.EventSortException;
import com.example.ewmmainservice.exception.ForbiddenException;
import com.example.ewmmainservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository repository;
    private final EventMapper mapper;

    @Override
    public List<EventShortDto> getEventsSort(String text,
                                             List<Long> categories,
                                             Boolean paid,
                                             LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd,
                                             boolean onlyAvailable,
                                             String sort,
                                             int from,
                                             int size) {
        log.info("EventPublicService: Получение списка событий с сортировкой по text={}, start={}, end={}.",
                text, rangeStart, rangeEnd);
        if (text.isBlank()) return Collections.emptyList();
        if (rangeStart == null) rangeStart = LocalDateTime.now();
        List<EventFullDto> events = repository.findEvents(text, categories, paid, rangeStart, rangeEnd)
                .stream()
                .map(mapper::toFullDto)
                .collect(Collectors.toList());
        if (onlyAvailable) {
            events = events.stream()
                    .filter(e -> e.getConfirmedRequests() < e.getParticipantLimit() || e.getParticipantLimit() == 0)
                    .collect(Collectors.toList());
        }
        if (sort != null) {
            EventSort extractedSort;
            try {
                extractedSort = EventSort.valueOf(sort);
            } catch (IllegalArgumentException e) {
                throw new EventSortException("EventPublicService: " +
                        "При получени списка событий передан некорректный вид сортировки sort=" + sort);
            }
            switch (extractedSort) {
                case EVENT_DATE:
                    events = events.stream()
                            .sorted(Comparator.comparing(EventFullDto::getEventDate))
                            .collect(Collectors.toList());
                    break;
                case VIEWS:
                    events = events.stream()
                            .sorted(Comparator.comparingLong(EventFullDto::getViews))
                            .collect(Collectors.toList());
                    break;
            }
        }
        return events.stream()
                .skip(from)
                .limit(size)
                .map(mapper::toShortDtoFromFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto findEventById(Long eventId) {
        log.info("EventPublicService: Получение подробной информации об опубликованном событии с id={}.", eventId);
        Event event = repository.findById(eventId)
                .orElseThrow(() ->
                        new NotFoundException("EventPublicService: Не найдено событие с id=" + eventId));
        if (!EventState.PUBLISHED.equals(event.getState())) {
            throw new ForbiddenException("EventPublicService: " +
                    "Запрошена информация о неопубликованом событии с id=" + eventId);
        }
        return mapper.toFullDto(event);
    }
}
