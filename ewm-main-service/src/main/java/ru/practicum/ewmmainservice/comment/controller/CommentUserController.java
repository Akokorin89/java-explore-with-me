package ru.practicum.ewmmainservice.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.comment.dto.CommentDto;
import ru.practicum.ewmmainservice.comment.dto.CommentUpdateDto;
import ru.practicum.ewmmainservice.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/comments")
public class CommentUserController {

    private final CommentService commentService;

    @PostMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto saveComment(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody CommentDto commentDto) {
        return commentService.saveComment(commentDto, userId, eventId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable Long commentId,
                                    @PathVariable Long userId,
                                    @RequestBody CommentUpdateDto commentDto) {
        return commentService.updateComment(commentId, userId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void userDeleteComment(@PathVariable Long userId,
                                  @PathVariable Long commentId) {
        commentService.userDeleteComment(commentId, userId);
    }

    @GetMapping
    public List<CommentDto> getAllCommentsByUser(@PathVariable Long userId) {
        return commentService.getAllCommentsByUser(userId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentUserById(@PathVariable Long userId,
                                         @PathVariable Long commentId) {
        return commentService.getCommentUserById(userId, commentId);
    }
}
