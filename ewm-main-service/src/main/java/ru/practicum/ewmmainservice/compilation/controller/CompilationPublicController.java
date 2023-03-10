package ru.practicum.ewmmainservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.compilation.service.CompilationPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationPublicController {
    private final CompilationPublicService service;

    @GetMapping // Получение подборок событий
    public List<CompilationDto> findAll(@RequestParam(required = false) Boolean pinned,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                        @Positive @RequestParam(defaultValue = "10") int size) {
        return service.findAll(pinned, from, size);
    }

    @GetMapping("/{compId}") // Получение подборки событий по идентификатору
    public CompilationDto findById(@PathVariable Long compId) {
        return service.findById(compId);
    }
}
