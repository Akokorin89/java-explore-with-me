package ru.practicum.ewmmainservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.user.dto.UserCreateDto;
import ru.practicum.ewmmainservice.user.dto.UserDto;
import ru.practicum.ewmmainservice.user.service.UserAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserAdminController {
    private final UserAdminService service;

    @GetMapping // Получение информации о пользователях
    public List<UserDto> findAll(@RequestParam(required = false) Set<Long> ids,
                                 @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                 @Positive @RequestParam(defaultValue = "10") int size) {
        if (ids != null && !ids.isEmpty()) {
            return service.getUsersByIds(ids);
        }
        return service.findAll(ids, from, size);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping // Добавление нового пользователя
    public UserDto save(@Valid @RequestBody UserCreateDto userCreateDto) {
        return service.save(userCreateDto);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}") // Удаление пользователя
    public void delete(@PathVariable Long userId) {
        service.deleteById(userId);
    }
}
