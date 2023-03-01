package com.example.ewmmainservice.request.model;

import com.example.ewmmainservice.request.dto.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "event_id")
    @Column(name = "created")
    private LocalDateTime created;
    private Long eventId;
    @JoinColumn(name = "requester_id")
    private Long requesterId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;
}