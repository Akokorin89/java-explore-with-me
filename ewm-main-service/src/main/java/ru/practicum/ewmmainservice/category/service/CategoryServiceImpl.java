package ru.practicum.ewmmainservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.category.mapper.CategoryMapper;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.category.repository.CategoryRepository;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.ConflictException;
import ru.practicum.ewmmainservice.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CategoryDto update(Long catId, CategoryDto categoryDto) {
        Category categoryUpdate = CategoryMapper.toUpdate(categoryDto);
        Category category = findByIdValid(catId);
        category.setName(categoryUpdate.getName());
        log.info("CategoryAdminService: Обновлена информация о категории");
        return CategoryMapper.toDto(repository.save(category));
    }

    @Transactional
    @Override
    public CategoryDto save(NewCategoryDto newCategoryDto) {
        log.info("CategoryAdminService: Сохранение категории с названием={}.", newCategoryDto.getName());
        Category category = CategoryMapper.toModel(newCategoryDto);
        Category saved = repository.save(category);
        log.info("CategoryAdminService: Сохранение категории={}.", saved);
        return CategoryMapper.toDto(saved);
    }

    @Transactional
    @Override
    public void delete(Long categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException("CategoryAdminService: Не найдена категория событий с id=" + categoryId));

        if (!eventRepository.findAllByCategoryId(categoryId).isEmpty()) {
            throw new ConflictException("CategoryAdminService: Невозможно удалить категорию, когда в ней есть события.");
        }
        repository.delete(category);
        log.info("CategoryAdminService: Удалена информация о категории №={}.", categoryId);
    }


    @Override
    public List<CategoryDto> findAll(int from, int size) {
        log.info("PublicCategoriesService: Получение списка категорий.");
        return repository.findAll(PageRequest.of(from / size, size))
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long categoryId) {
        log.info("PublicCategoriesService: Получение категории по ее идентификатору.");
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("PublicCategoriesService: Не найдена категория с id=" +
                        categoryId));
        return CategoryMapper.toDto(category);
    }

    private Category findByIdValid(Long catId) {
        return repository.findById(catId)
                .orElseThrow(() ->
                        new NotFoundException("CategoryAdminService: Не найдена категория с id=" + catId));
    }
}
