package ru.practicum.ewmmainservice.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.category.service.CategoryAdminService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryAdminService service;

    @PatchMapping("/{catId}") // изменение категории
    public CategoryDto update(@PathVariable Long catId, @Valid @RequestBody CategoryDto categoryDto) {
        return service.update(catId, categoryDto);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping // добавление новой категории
    public CategoryDto save(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return service.save(newCategoryDto);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catId}") // удаление категории
    public void deleteCategory(@PathVariable Long catId) {
        service.delete(catId);
    }
}
