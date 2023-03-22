package ru.practicum.ewmmainservice.comment.model;

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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "edited_on")
    private LocalDateTime editedOn;
    @Column(name = "is_edited")
    private boolean isEdited;
}
