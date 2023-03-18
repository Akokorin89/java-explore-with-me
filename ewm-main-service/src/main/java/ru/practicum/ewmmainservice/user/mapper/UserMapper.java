package ru.practicum.ewmmainservice.user.mapper;


import lombok.experimental.UtilityClass;
import ru.practicum.ewmmainservice.user.dto.UserCreateDto;
import ru.practicum.ewmmainservice.user.dto.UserDto;
import ru.practicum.ewmmainservice.user.dto.UserShortDto;
import ru.practicum.ewmmainservice.user.model.User;


@UtilityClass
public class UserMapper {

    public static User toModel(UserCreateDto userCreateDto) {
        return User.builder()
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserShortDto toShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
