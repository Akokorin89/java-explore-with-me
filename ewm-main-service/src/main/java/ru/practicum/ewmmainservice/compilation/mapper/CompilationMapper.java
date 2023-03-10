package ru.practicum.ewmmainservice.compilation.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmmainservice.compilation.model.Compilation;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.event.service.EventPrivateService;

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
