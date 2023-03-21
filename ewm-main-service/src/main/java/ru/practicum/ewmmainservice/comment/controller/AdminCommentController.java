package ru.practicum.ewmmainservice.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.comment.dto.CommentDto;
import ru.practicum.ewmmainservice.comment.dto.CommentUpdateDto;
import ru.practicum.ewmmainservice.comment.service.CommentService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments/{commentId}")
public class AdminCommentController {

    private final CommentService commentService;

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void adminDeleteComment(@PathVariable Long commentId) {
        commentService.adminDeleteComment(commentId);
    }

    @PatchMapping
    public CommentDto updateCommentAdmin(@PathVariable Long commentId, @RequestBody CommentUpdateDto commentUpdateDto) {
        return commentService.updateCommentAdmin(commentId, commentUpdateDto);
    }
}
