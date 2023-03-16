package ru.practicum.ewmmainservice.event.dto;

import lombok.*;
import ru.practicum.ewmmainservice.participationRequest.model.StatusRequest;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventReqStatusUpdateReqDto {

    private List<Long> requestIds;

    private StatusRequest status;
}
