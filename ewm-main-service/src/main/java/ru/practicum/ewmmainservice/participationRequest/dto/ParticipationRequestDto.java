package ru.practicum.ewmmainservice.participationRequest.dto;

import lombok.*;
import ru.practicum.ewmmainservice.participationRequest.model.StatusRequest;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {

    private Long id;

    private Long event;

    private String created;

    private Long requester;

    private StatusRequest status;
}
