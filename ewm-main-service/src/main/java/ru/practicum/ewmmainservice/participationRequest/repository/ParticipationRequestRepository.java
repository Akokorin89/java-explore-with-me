package ru.practicum.ewmmainservice.participationRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.participationRequest.model.ParticipationRequest;
import ru.practicum.ewmmainservice.participationRequest.model.StatusRequest;

import java.util.List;
import java.util.Optional;


public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    ParticipationRequest findByEventIdAndRequesterId(Long eventId, Long userId);

    List<ParticipationRequest> findAllByRequesterId(Long userId);

    Optional<ParticipationRequest> findByIdAndRequesterId(Long requestId, Long userId);

    List<ParticipationRequest> findAllByEventIdAndEventInitiatorId(Long eventId, Long userId);

    Integer countParticipationByEventIdAndStatus(Long eventId, StatusRequest confirmed);

    Optional<ParticipationRequest> findByIdAndEvent_Id(Long id, Long eventId);
}
