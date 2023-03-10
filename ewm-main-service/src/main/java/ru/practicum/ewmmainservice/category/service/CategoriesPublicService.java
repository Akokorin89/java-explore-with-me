package ru.practicum.ewmmainservice.category.service;

import ru.practicum.ewmmainservice.category.dto.CategoryDto;

import java.util.List;

public interface CategoriesPublicService {

    List<CategoryDto> findAll(int from, int size);

    CategoryDto findById(Long categoryId);
}
