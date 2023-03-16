package ru.practicum.ewmmainservice.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationUpdateDto {

    private String title;

    private Boolean pinned;

    private List<Long> events;
}
