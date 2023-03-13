package ru.practicum.ewmmainservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.mapper.CategoryMapper;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.category.repository.CategoryRepository;
import ru.practicum.ewmmainservice.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriesPublicServiceImpl implements CategoriesPublicService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> findAll(int from, int size) {
        log.info("PublicCategoriesService: Получение списка категорий.");
        return repository.findAll(PageRequest.of(from / size, size))
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long categoryId) {
        log.info("PublicCategoriesService: Получение категории по ее идентификатору.");
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("PublicCategoriesService: Не найдена категория с id=" +
                        categoryId));
        return mapper.toDto(category);
    }
}
