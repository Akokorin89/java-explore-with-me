package ru.practicum.ewmmainservice.compilation.service;

import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.compilation.dto.NewCompilationDto;

public interface CompilationAdminService {

    CompilationDto save(NewCompilationDto newCompilationDto);

    void delete(Long compId);

    void saveOrDeleteEventInCompilation(Long compId, Long eventId, boolean isDeleting);

    void changeCompilationPin(Long compId, boolean isPin);
}
