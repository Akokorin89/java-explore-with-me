package com.example.ewmmainservice.category.service;

import com.example.ewmmainservice.category.dto.CategoryDto;

import java.util.List;

public interface CategoriesPublicService {

    List<CategoryDto> findAll(int from, int size);

    CategoryDto findById(Long categoryId);
}
