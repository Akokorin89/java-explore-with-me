package com.example.ewmmainservice.event.service;

import com.example.ewmmainservice.event.dto.EventAdminUpdate;
import com.example.ewmmainservice.event.dto.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAdminService {

    List<EventFullDto> searchEvents(List<Long> users,
                                    List<String> states,
                                    List<Long> categories,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    int from,
                                    int size);

    EventFullDto update(EventAdminUpdate eventAdminUpdate, Long eventId);

    EventFullDto changeEventState(Long eventId, boolean isPublish);
}
