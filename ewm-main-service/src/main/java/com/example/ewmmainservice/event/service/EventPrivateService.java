package com.example.ewmmainservice.event.service;

import com.example.ewmmainservice.event.dto.EventFullDto;
import com.example.ewmmainservice.event.dto.EventUpdateDto;
import com.example.ewmmainservice.event.dto.NewEventDto;
import com.example.ewmmainservice.event.model.Event;
import com.example.ewmmainservice.request.dto.RequestDto;

import java.util.List;

public interface EventPrivateService {

    List<EventFullDto> findEventByInitiatorId(Long userId, int from, int size);

    EventFullDto update(EventUpdateDto eventUpdateDto, Long userId);


    EventFullDto save(NewEventDto newEventDto, Long userId);


    EventFullDto findEventInfoByInitiator(Long userId, Long eventId);


    EventFullDto cancel(Long userId, Long eventId);


    List<RequestDto> getAllRequestsByEventId(Long userId, Long eventId);


    RequestDto changeRequestStatus(Long userId, Long eventId, Long requestId, boolean isApproved);

    Event findEventById(Long eventId);
}
