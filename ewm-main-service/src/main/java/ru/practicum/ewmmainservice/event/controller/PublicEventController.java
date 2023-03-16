package ru.practicum.ewmmainservice.event.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.client.EventClient;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;
    private final EventClient client;

    public PublicEventController(EventService eventService, EventClient client) {
        this.eventService = eventService;
        this.client = client;
    }


    @GetMapping // Получение событий с возможностью фильтрации
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categoryIds,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(required = false) String sort,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                         @Positive @RequestParam(defaultValue = "10") int size,
                                         HttpServletRequest httpServletRequest) {
        client.createHit(httpServletRequest);
        return eventService.getEvents(text, categoryIds, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}") // Получение подробной информации об опубликованном событии по его идентификатору
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        client.createHit(httpServletRequest);
        return eventService.getEventById(id);
    }
}
