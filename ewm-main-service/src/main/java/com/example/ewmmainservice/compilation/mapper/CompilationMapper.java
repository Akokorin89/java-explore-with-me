package com.example.ewmmainservice.compilation.mapper;

import com.example.ewmmainservice.compilation.dto.CompilationDto;
import com.example.ewmmainservice.compilation.dto.NewCompilationDto;
import com.example.ewmmainservice.compilation.model.Compilation;
import com.example.ewmmainservice.event.dto.EventShortDto;
import com.example.ewmmainservice.event.model.Event;
import com.example.ewmmainservice.event.service.EventPrivateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventPrivateService service;

    public Compilation toModel(NewCompilationDto newCompilationDto) {
        Set<Event> events = newCompilationDto.getEvents()
                .stream()
                .map(service::findEventById)
                .collect(Collectors.toSet());
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(events)
                .build();
    }

    public CompilationDto toDto(Compilation compilation, List<EventShortDto> events) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(events)
                .build();
    }
}
