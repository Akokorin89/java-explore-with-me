package ru.practicum.ewmmainservice.event.dto;


import lombok.*;
import org.jetbrains.annotations.NotNull;
import ru.practicum.ewmmainservice.location.dto.LocationDto;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotBlank
    private String annotation;

    private Long category;

    @NotBlank
    private String description;

    @NotBlank
    private String eventDate;

    @NotNull
    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    @NotBlank
    private String title;
}
