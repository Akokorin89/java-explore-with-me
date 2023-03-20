package ru.practicum.ewmmainservice.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 3, max = 120)
    private String name;
}
