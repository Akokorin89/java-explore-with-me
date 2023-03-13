package ru.practicum.ewmmainservice.category.controller;

import lombok.RequiredArgsConstructor;
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

    @PatchMapping // изменение категории
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        return service.update(categoryDto);
    }

    @PostMapping // добавление новой категории
    public CategoryDto save(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return service.save(newCategoryDto);
    }

    @DeleteMapping("/{catId}") // удаление категории
    public void deleteCategory(@PathVariable Long catId) {
        service.delete(catId);
    }
}
