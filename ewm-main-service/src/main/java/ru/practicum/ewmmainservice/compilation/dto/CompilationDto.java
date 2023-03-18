package ru.practicum.ewmmainservice.compilation.dto;

import lombok.*;
import ru.practicum.ewmmainservice.event.model.Event;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Long id;

    private String title;

    private Boolean pinned;

    private Set<Event> events;
}
