package ru.practicum.ewmmainservice.compilation.service;


import ru.practicum.ewmmainservice.compilation.dto.CompilationCreateDto;
import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.compilation.dto.CompilationUpdateDto;

import java.util.List;

public interface CompilationService {

    CompilationDto saveCompilation(CompilationCreateDto compilationCreateDto);

    CompilationDto updateCompilation(Long compId, CompilationUpdateDto compilationUpdateDto);

    List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);

    void deleteCompilation(Long compId);
}
