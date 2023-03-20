package ru.practicum.ewmmainservice.participationRequest.service;


import ru.practicum.ewmmainservice.event.dto.EventReqStatusUpdateReqDto;
import ru.practicum.ewmmainservice.event.dto.RequestStatusUpdateResponse;
import ru.practicum.ewmmainservice.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestDto saveParticipationRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getParticipationRequests(Long userId);

    List<ParticipationRequestDto> getParticipationRequests(Long eventId, Long userId);

    RequestStatusUpdateResponse updateParticipationRequest(Long eventId, Long userId, EventReqStatusUpdateReqDto updateReqDto);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);
}
