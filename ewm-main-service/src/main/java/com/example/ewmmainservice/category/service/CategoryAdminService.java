package com.example.ewmmainservice.category.service;

import com.example.ewmmainservice.category.dto.CategoryDto;
import com.example.ewmmainservice.category.dto.NewCategoryDto;

public interface CategoryAdminService {

    CategoryDto update(CategoryDto categoryDto);

    CategoryDto save(NewCategoryDto newCategoryDto);

    void delete(Long categoryId);
}
