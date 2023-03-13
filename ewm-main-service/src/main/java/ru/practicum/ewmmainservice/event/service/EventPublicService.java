package ru.practicum.ewmmainservice.event.service;

import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventPublicService {

    List<EventShortDto> getEventsSort(String text,
                                      List<Long> categories,
                                      Boolean paid,
                                      LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd,
                                      boolean onlyAvailable,
                                      String sort,
                                      int from,
                                      int size);

    EventFullDto findEventById(Long eventId);

}
