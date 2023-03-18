package ru.practicum.ewmmainservice.category.service;

import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto update(Long catId, CategoryDto categoryDto);

    CategoryDto save(NewCategoryDto newCategoryDto);

    void delete(Long categoryId);

    List<CategoryDto> findAll(int from, int size);

    CategoryDto findById(Long categoryId);
}
