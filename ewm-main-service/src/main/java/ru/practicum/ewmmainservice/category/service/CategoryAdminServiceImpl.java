package ru.practicum.ewmmainservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.category.mapper.CategoryMapper;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.category.repository.CategoryRepository;
import ru.practicum.ewmmainservice.exception.ForbiddenException;
import ru.practicum.ewmmainservice.exception.NotFoundException;


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
