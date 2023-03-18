package ru.practicum.ewmmainservice.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmmainservice.event.model.Event;
import ru.practicum.ewmmainservice.event.model.State;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
            "AND ((:states) IS NULL OR e.state IN :states) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories)")
    Page<Event> searchEventsByAdmin(List<Long> users, List<State> states, List<Long> categories, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (lower(e.annotation) like lower(concat('%', :text, '%')) " +
            "OR lower(e.description) like lower(concat('%', :text, '%'))) " +
            "AND ((:categoryIds) IS NULL OR e.category.id IN :categoryIds) " +
            "AND e.paid = :paid " +
            "AND e.state IN :state")
    Page<Event> searchEvents(String text, List<Long> categoryIds, Boolean paid, State state, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE e.id IN :ids " +
            "ORDER BY e.id")
    Set<Event> findEventsByIds(@Param("ids") List<Long> events);

    Set<Event> findAllByCategoryId(Long categoryId);

}
