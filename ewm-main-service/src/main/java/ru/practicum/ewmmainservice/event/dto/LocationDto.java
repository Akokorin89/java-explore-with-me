package ru.practicum.ewmmainservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Float lat;
    private Float lon;
}
