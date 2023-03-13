package ru.practicum.ewmmainservice.request.dto;

import lombok.Data;

@Data
public class NewUserRequest {

    private String email;

    private String name;

}
