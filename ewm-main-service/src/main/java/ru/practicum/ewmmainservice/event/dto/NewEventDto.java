package ru.practicum.ewmmainservice.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmainservice.location.dto.LocationDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;


    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

    @NotBlank
    private String title;
}
