package ru.practicum.ewmmainservice.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.service.CategoriesPublicService;

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
