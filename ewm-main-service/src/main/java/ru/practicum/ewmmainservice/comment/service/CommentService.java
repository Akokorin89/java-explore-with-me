package ru.practicum.ewmmainservice.comment.service;


import ru.practicum.ewmmainservice.comment.dto.CommentDto;
import ru.practicum.ewmmainservice.comment.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {

    CommentDto saveComment(CommentDto commentDto, Long userId, Long eventId);

    CommentDto updateComment(Long commentId, Long userId, CommentUpdateDto commentUpdateDto);

    List<CommentDto> getAllCommentsByUser(Long userId);

    CommentDto getCommentUserById(Long userId, Long commentId);

    List<CommentDto> getAllCommentsByEvent(Long eventId);

    void userDeleteComment(Long commentId, Long userId);

    void adminDeleteComment(Long commentId);

    CommentDto updateCommentAdmin(Long commentId, CommentUpdateDto commentUpdateDto);

    CommentDto getCommentEventById(Long event, Long commentId);
}
