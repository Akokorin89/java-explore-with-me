package ru.practicum.ewmmainservice.event.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmmainservice.event.model.Constant.DATE_TIME_FORMATTER;
import static ru.practicum.ewmmainservice.event.model.State.PENDING;
import static ru.practicum.ewmmainservice.location.mapper.LocationMapper.toLocationDto;


@UtilityClass
public class EventMapper {

    public static EventFullDto toEventDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .createdOn(event.getCreatedOn().format(DATE_TIME_FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .location(toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }

    public static Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .state(PENDING)
                .createdOn(LocalDateTime.now())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .eventDate(event.getEventDate())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static List<EventShortDto> toEventsDto(Page<Event> events) {
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }
}