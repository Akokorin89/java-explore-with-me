package ru.practicum.ewmmainservice.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    private String name;
}
