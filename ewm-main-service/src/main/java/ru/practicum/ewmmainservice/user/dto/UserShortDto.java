package ru.practicum.ewmmainservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserShortDto {
    private Long id;
    private String name;

}
