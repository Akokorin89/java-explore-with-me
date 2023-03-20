package ru.practicum.ewmmainservice.participationRequest.model;

import lombok.*;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "requests")
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @Column(name = "created")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private StatusRequest status;

}
