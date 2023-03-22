package ru.practicum.ewmmainservice.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.comment.dto.CommentDto;
import ru.practicum.ewmmainservice.comment.dto.CommentUpdateDto;
import ru.practicum.ewmmainservice.comment.model.Comment;
import ru.practicum.ewmmainservice.comment.repository.CommentRepository;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.event.model.State;
import ru.practicum.ewmmainservice.event.repository.EventRepository;
import ru.practicum.ewmmainservice.exception.ConflictException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.user.model.User;
import ru.practicum.ewmmainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewmmainservice.comment.mapper.CommentMapper.*;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentDto saveComment(CommentDto commentDto, Long userId, Long eventId) {
        User user = getUser(userId);
        Event event = getEvent(eventId);

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("CommentService: Комментарий можно оставить только под опубликованным событием.");
        }
        Comment comment = toComment(commentDto, user, event);
        //Время задается при маппинге
        log.info("CommentService: Комментарий добавлен.");
        return toCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto updateComment(Long commentId, Long userId, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, userId)
                .orElseThrow(() -> new ConflictException("CommentService: Только автор может изменить комментарий."));

        if (commentUpdateDto.getText() != null && !commentUpdateDto.getText().isBlank()) {
            comment.setText(commentUpdateDto.getText());
            comment.setEditedOn(LocalDateTime.now());
            comment.setEdited(true);
        }
        log.info("CommentService: Комментарий изменен.");
        return toCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByUser(Long userId) {
        User user = getUser(userId);
        log.info("CommentService: Получен список всех комментариев пользователя.");
        return toCommentsDto(commentRepository.findAllByUser(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByEvent(Long eventId) {
        Event event = getEvent(eventId);
        log.info("CommentService: Получен список всех комментариев события.");
        return toCommentsDto(commentRepository.findAllByEvent(event));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentUserById(Long userId, Long commentId) {
        getUser(userId);
        Comment comment = getComment(commentId);
        log.info("CommentService: Получен комментарий id ={} пользователя id = {}.", commentId, userId);
        return toCommentDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentEventById(Long eventId, Long commentId) {
        getEvent(eventId);
        Comment comment = getComment(commentId);
        log.info("CommentService: Получен комментарий id ={} к событию id = {}.", commentId, eventId);
        return toCommentDto(comment);
    }


    @Override
    public void userDeleteComment(Long commentId, Long userId) {
        getUser(userId);
        Comment comment = getComment(commentId);

        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new ConflictException("CommentService: Только автор может удалить комментарий.");
        }
        commentRepository.deleteById(commentId);
        log.info("CommentService: Комментарий удален пользователем.");
    }

    @Override
    public void adminDeleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("CommentService: Комментарий не найден.");
        }
        commentRepository.deleteById(commentId);
        log.info("CommentService: Комментарий удален администратором.");
    }

    @Override
    public CommentDto updateCommentAdmin(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment comment = getComment(commentId);
        comment.setText(commentUpdateDto.getText());
        comment.setEditedOn(LocalDateTime.now());
        comment.setEdited(true);
        log.info("CommentService: Комментарий изменен.");
        return toCommentDto(commentRepository.save(comment));
    }


    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("CommentService: Неверный ID пользователя."));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("CommentService: Неверный ID события."));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("CommentService: Неверный ID комментария."));
    }
}
