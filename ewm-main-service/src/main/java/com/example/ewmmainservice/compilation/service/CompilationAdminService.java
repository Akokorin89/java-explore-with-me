package com.example.ewmmainservice.compilation.service;

import com.example.ewmmainservice.compilation.dto.CompilationDto;
import com.example.ewmmainservice.compilation.dto.NewCompilationDto;

public interface CompilationAdminService {

    CompilationDto save(NewCompilationDto newCompilationDto);

    void delete(Long compId);

    void saveOrDeleteEventInCompilation(Long compId, Long eventId, boolean isDeleting);

    void changeCompilationPin(Long compId, boolean isPin);
}
