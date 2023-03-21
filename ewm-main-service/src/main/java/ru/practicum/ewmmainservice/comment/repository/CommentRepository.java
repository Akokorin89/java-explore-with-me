package ru.practicum.ewmmainservice.comment.repository;

import org.hibernate.annotations.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.comment.model.Comment;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.user.model.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long id, Long userId);


    @OrderBy(clause = "Created ASC")
    List<Comment> findAllByUser(User user);

    @OrderBy(clause = "Created DESC")
    List<Comment> findAllByEvent(Event event);
}
