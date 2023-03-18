package ru.practicum.ewmmainservice.category.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.category.model.Category;

@UtilityClass
public class CategoryMapper {

    public static Category toModel(NewCategoryDto categoryCreateDto) {
        return Category.builder()
                .name(categoryCreateDto.getName())
                .build();
    }

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toUpdate(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
