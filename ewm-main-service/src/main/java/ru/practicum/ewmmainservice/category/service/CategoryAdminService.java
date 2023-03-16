package ru.practicum.ewmmainservice.category.service;

import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;

public interface CategoryAdminService {

    CategoryDto update(Long catId, CategoryDto categoryDto);

    CategoryDto save(NewCategoryDto newCategoryDto);

    void delete(Long categoryId);
}
