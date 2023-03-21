package ru.practicum.ewmmainservice.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmmainservice.comment.dto.CommentDto;
import ru.practicum.ewmmainservice.comment.model.Comment;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getUser().getName())
                .created(comment.getCreated())
                .build();
    }

    public static Comment toComment(CommentDto commentDto, User user, Event event) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .user(user)
                .event(event)
                .created(LocalDateTime.now())
                .build();
    }

    public static List<CommentDto> toCommentsDto(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
