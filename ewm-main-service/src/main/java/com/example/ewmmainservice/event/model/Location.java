package com.example.ewmmainservice.event.model;

import lombok.*;

import javax.persistence.Embeddable;

@Setter
@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Float lat;
    private Float lon;
}
