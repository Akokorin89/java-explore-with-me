package ru.practicum.ewmmainservice.request.service;

import ru.practicum.ewmmainservice.request.dto.RequestDto;

import java.util.List;

public interface RequestPrivateService {

    List<RequestDto> findRequests(Long userId);

    RequestDto save(Long userId, Long eventId);

    RequestDto cancelRequest(Long userId, Long requestId);
}
