package ru.practicum.ewmmainservice.participationRequest.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmmainservice.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.participationRequest.model.ParticipationRequest;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmmainservice.event.model.Constant.DATE_TIME_FORMATTER;

@UtilityClass
public class ParticipationRequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .status(request.getStatus())
                .created(request.getCreated().format(DATE_TIME_FORMATTER))
                .build();
    }

    public static List<ParticipationRequestDto> toParticipationRequestsDto(List<ParticipationRequest> requests) {
        return requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }
}
