package ru.practicum.ewmmainservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @Email
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    private String name;
}
