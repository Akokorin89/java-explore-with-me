package ru.practicum.ewmmainservice.compilation.service;

import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationPublicService {

    List<CompilationDto> findAll(Boolean pinned, int from, int size);

    CompilationDto findById(Long compId);
}
