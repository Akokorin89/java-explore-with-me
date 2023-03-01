package com.example.ewmmainservice.user.service;

import com.example.ewmmainservice.exception.NotFoundException;
import com.example.ewmmainservice.user.dto.UserCreateDto;
import com.example.ewmmainservice.user.dto.UserDto;
import com.example.ewmmainservice.user.mapper.UserMapper;
import com.example.ewmmainservice.user.model.User;
import com.example.ewmmainservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<UserDto> findAll(Set<Long> ids, int from, int size) {
        log.info("UserAdminService: Получение информации о пользователях с параметрами from={}, size={}", from, size);
        return repository.findAll(PageRequest.of(from / size, size))
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto save(UserCreateDto userCreateDto) {
        User user = mapper.toModel(userCreateDto);
        User saved = repository.save(user);
        log.info("UserAdminService: Cохранен пользователь=" + saved);
        return mapper.toDto(saved);
    }

    @Transactional
    @Override
    public void deleteById(Long userId) {
        boolean isExists = repository.existsById(userId);
        if (!isExists) {
            throw new NotFoundException("UserAdminService: Не найден пользователь для удаления с id=" + userId);
        }
        repository.deleteById(userId);
        log.info("UserAdminService: Удален пользователь с id=" + userId);
    }

    @Override
    public List<UserDto> getUsersByIds(Set<Long> ids) {
        return repository.findUsersByIdIn(ids)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
