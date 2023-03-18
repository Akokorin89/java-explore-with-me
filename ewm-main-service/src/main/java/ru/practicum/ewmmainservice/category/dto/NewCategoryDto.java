package ru.practicum.ewmmainservice.category.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    private String name;
}
