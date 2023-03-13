package ru.practicum.ewmmainservice.event.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.client.StatsClient;
import ru.practicum.ewmmainservice.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.event.dto.EventState;
import ru.practicum.ewmmainservice.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.event.model.Location;
import ru.practicum.ewmmainservice.request.repository.RequestRepository;
import ru.practicum.ewmmainservice.user.dto.UserShortDto;
import ru.practicum.ewmmainservice.user.model.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final RequestRepository requestRepository;
    private final StatsClient client;

    public Event toModel(NewEventDto newEventDto, Long userId) {
        return Event.builder()
                .id(null)
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .category(new Category(newEventDto.getCategory(), null))
                .createdOn(LocalDateTime.now())
                .eventDate(newEventDto.getEventDate())
                .initiator(new User(userId, null, null))
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .publishedOn(null)
                .requestModeration(newEventDto.getRequestModeration())
                .state(EventState.PENDING)
                .location(new Location(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon()))
                .build();
    }

    public EventFullDto toFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .location(LocationMapper.toDto(event.getLocation()))
                .confirmedRequests(requestRepository.getConfirmedRequests(event.getId()))
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString())
                .title(event.getTitle())
                .views(getViews(event.getId()))
                .build();
    }

    public EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .confirmedRequests(requestRepository.getConfirmedRequests(event.getId()))
                .eventDate(event.getEventDate())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(getViews(event.getId()))
                .build();
    }

    public Event toModelFromShortDto(EventShortDto eventShortDto) {
        return Event.builder()
                .id(eventShortDto.getId())
                .title(eventShortDto.getTitle())
                .annotation(eventShortDto.getAnnotation())
                .eventDate(eventShortDto.getEventDate())
                .paid(eventShortDto.getPaid())
                .build();
    }

    public EventShortDto toShortDtoFromFullDto(EventFullDto eventFullDto) {
        return EventShortDto.builder()
                .id(eventFullDto.getId())
                .annotation(eventFullDto.getAnnotation())
                .category(eventFullDto.getCategory())
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .eventDate(eventFullDto.getEventDate())
                .initiator(eventFullDto.getInitiator())
                .paid(eventFullDto.getPaid())
                .title(eventFullDto.getTitle())
                .views(eventFullDto.getViews())
                .build();
    }

    private Integer getViews(Long eventId) {
        String uri = "/events/" + eventId;
        return (Integer) client.getViews(uri);
    }
}
