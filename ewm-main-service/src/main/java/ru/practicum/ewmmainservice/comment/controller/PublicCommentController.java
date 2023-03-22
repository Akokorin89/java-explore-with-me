package ru.practicum.ewmmainservice.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmainservice.comment.dto.CommentDto;
import ru.practicum.ewmmainservice.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}/comments")
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllCommentsByEvent(@PathVariable Long eventId) {
        return commentService.getAllCommentsByEvent(eventId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentEventById(@PathVariable Long eventId,
                                          @PathVariable Long commentId) {
        return commentService.getCommentEventById(eventId, commentId);
    }
}
