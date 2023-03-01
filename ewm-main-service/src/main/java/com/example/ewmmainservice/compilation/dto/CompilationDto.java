package com.example.ewmmainservice.compilation.dto;

import com.example.ewmmainservice.event.dto.EventShortDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompilationDto {

    List<EventShortDto> events;
    private Long id;
    private Boolean pinned;

    private String title;
}
