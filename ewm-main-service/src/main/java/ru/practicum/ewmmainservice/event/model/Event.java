package ru.practicum.ewmmainservice.event.model;

import lombok.*;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.location.model.Location;
import ru.practicum.ewmmainservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation")
    private String annotation;

    @ManyToOne
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    private Location location;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "request_moderation", columnDefinition = "true")
    private Boolean requestModeration;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    private User initiator;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Enumerated(EnumType.STRING)
    private State state;

    @Builder.Default
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;

    @Builder.Default
    private Long views = 0L;
}
