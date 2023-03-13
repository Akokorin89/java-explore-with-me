package ru.practicum.ewmmainservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmmainservice.compilation.service.CompilationAdminService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {
    private final CompilationAdminService service;

    @PostMapping // добавление новой подборки
    public CompilationDto save(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return service.save(newCompilationDto);
    }

    @DeleteMapping("/{compId}") // удаление подборки
    public void delete(@PathVariable Long compId) {
        service.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}") // удалить событие из подборки
    public void deleteEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        service.saveOrDeleteEventInCompilation(compId, eventId, true);
    }

    @PatchMapping("/{compId}/events/{eventId}") // добавить событие в подборку
    public void saveEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        service.saveOrDeleteEventInCompilation(compId, eventId, false);
    }

    @DeleteMapping("/{compId}/pin") // открепить подборку на главной странице
    public void unpin(@PathVariable Long compId) {
        service.changeCompilationPin(compId, true);
    }

    @PatchMapping("/{compId}/pin") // закрепить подборку на главной странице
    public void pin(@PathVariable Long compId) {
        service.changeCompilationPin(compId, false);
    }
}
