package com.example.ewmmainservice.event.repository;

import com.example.ewmmainservice.event.dto.EventState;
import com.example.ewmmainservice.event.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    @Modifying
    @Query("update Event e set e.state = ?1 where e.id = ?2")
    void cancelEvent(EventState state, Long eventId);

    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:text) IS NULL " +
            "OR UPPER(e.annotation) LIKE UPPER(CONCAT('%', :text, '%')) " +
            "OR UPPER(e.description) LIKE UPPER(CONCAT('%', :text, '%'))) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "AND ((:paid) IS NULL OR e.paid = :paid) " +
            "AND (e.eventDate >= :rangeStart) " +
            "AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= :rangeEnd)")
    List<Event> findEvents(String text,
                           List<Long> categories,
                           Boolean paid,
                           LocalDateTime rangeStart,
                           LocalDateTime rangeEnd);

    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
            "AND ((:states) IS NULL OR e.state IN :states) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "AND (e.eventDate >= :rangeStart) " +
            "AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= :rangeEnd)")
    List<Event> searchEvents(List<Long> users,
                             List<EventState> states,
                             List<Long> categories,
                             LocalDateTime rangeStart,
                             LocalDateTime rangeEnd,
                             Pageable pageable);
}