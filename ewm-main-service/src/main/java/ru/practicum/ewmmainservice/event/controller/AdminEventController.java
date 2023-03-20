package ru.practicum.ewmmainservice.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.event.dto.AdminUpdateEventDto;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @GetMapping // Поиск событий
    public List<EventFullDto> getEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                               @RequestParam(required = false) List<String> states,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) String rangeStart,
                                               @RequestParam(required = false) String rangeEnd,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                               @Positive @RequestParam(defaultValue = "10") int size) {
        return eventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}") // Редактирование события
    public EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                           @RequestBody AdminUpdateEventDto eventDto) {
        return eventService.updateEventByAdmin(eventId, eventDto);
    }
}
