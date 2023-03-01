package com.example.ewmmainservice.user.service;


import com.example.ewmmainservice.user.dto.UserCreateDto;
import com.example.ewmmainservice.user.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface UserAdminService {

    List<UserDto> findAll(Set<Long> ids, int from, int size);

    UserDto save(UserCreateDto userCreateDto);

    void deleteById(Long userId);

    List<UserDto> getUsersByIds(Set<Long> ids);
}
