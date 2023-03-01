package com.example.ewmmainservice.category.service;

import com.example.ewmmainservice.category.dto.CategoryDto;
import com.example.ewmmainservice.category.dto.NewCategoryDto;
import com.example.ewmmainservice.category.mapper.CategoryMapper;
import com.example.ewmmainservice.category.model.Category;
import com.example.ewmmainservice.category.repository.CategoryRepository;
import com.example.ewmmainservice.exception.ForbiddenException;
import com.example.ewmmainservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category categoryUpdate = mapper.toUpdate(categoryDto);
        Category category = findById(categoryUpdate.getId());
        if (categoryUpdate.getName().equals(category.getName())) {
            throw new ForbiddenException("CategoryAdminService: Уже создана категория с названием=" +
                    categoryUpdate.getName());
        }
        category.setName(categoryUpdate.getName());
        Category updated = repository.save(category);
        log.info("CategoryAdminService: Обновлена информация о категории №={}.", updated.getId());
        return mapper.toDto(updated);
    }

    @Transactional
    @Override
    public CategoryDto save(NewCategoryDto newCategoryDto) {
        log.info("CategoryAdminService: Сохранение категории с названием={}.", newCategoryDto.getName());
        Category category = mapper.toModel(newCategoryDto);
        Category saved = repository.save(category);
        log.info("CategoryAdminService: Сохранение категории={}.", saved);
        return mapper.toDto(saved);
    }

    @Transactional
    @Override
    public void delete(Long categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException("CategoryAdminService: Не найдена категория событий с id=" + categoryId));
        repository.delete(category);
        log.info("CategoryAdminService: Удалена информация о категории №={}.", categoryId);
    }

    private Category findById(Long catId) {
        return repository.findById(catId)
                .orElseThrow(() ->
                        new NotFoundException("CategoryAdminService: Не найдена категория с id=" + catId));
    }
}
