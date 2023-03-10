package ru.practicum.ewmmainservice.user.mapper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.user.dto.UserCreateDto;
import ru.practicum.ewmmainservice.user.dto.UserDto;
import ru.practicum.ewmmainservice.user.dto.UserShortDto;
import ru.practicum.ewmmainservice.user.model.User;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public User toModel(UserCreateDto userCreateDto) {
        return User.builder()
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserShortDto toShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
