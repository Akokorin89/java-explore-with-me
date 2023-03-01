package com.example.ewmmainservice.category.controller;

import com.example.ewmmainservice.category.dto.CategoryDto;
import com.example.ewmmainservice.category.dto.NewCategoryDto;
import com.example.ewmmainservice.category.service.CategoryAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
