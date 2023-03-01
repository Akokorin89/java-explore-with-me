package com.example.ewmmainservice.category.controller;

import com.example.ewmmainservice.category.dto.CategoryDto;
import com.example.ewmmainservice.category.service.CategoriesPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoriesPublicController {
    private final CategoriesPublicService categoryService;

    @GetMapping // Получение категорий
    public List<CategoryDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                     @Positive @RequestParam(defaultValue = "10") int size) {
        return categoryService.findAll(from, size);
    }

    @GetMapping("/{categoryId}") // Получение информации о категории по ее идентификатору
    public CategoryDto findById(@PathVariable Long categoryId) {
        return categoryService.findById(categoryId);
    }
}
