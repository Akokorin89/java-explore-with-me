package ru.practicum.ewmmainservice.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {

    @NotBlank
    private String text;
}
