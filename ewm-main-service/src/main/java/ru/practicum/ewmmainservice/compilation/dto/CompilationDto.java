package ru.practicum.ewmmainservice.compilation.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;

import java.util.List;

@Data
@Builder
public class CompilationDto {

    List<EventShortDto> events;
    private Long id;
    private Boolean pinned;

    private String title;
}
