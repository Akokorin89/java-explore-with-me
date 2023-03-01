package com.example.ewmmainservice.category.mapper;

import com.example.ewmmainservice.category.dto.CategoryDto;
import com.example.ewmmainservice.category.dto.NewCategoryDto;
import com.example.ewmmainservice.category.model.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public Category toModel(NewCategoryDto categoryCreateDto) {
        return Category.builder()
                .name(categoryCreateDto.getName())
                .build();
    }

    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toUpdate(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
