package com.example.ewmmainservice.request.repository;

import com.example.ewmmainservice.request.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByEventId(Long eventId);

    @Query(value = "select count(*) " +
            "from requests " +
            "where event_id = ?1 " +
            "and status = 'CONFIRMED'",
            nativeQuery = true)
    Integer getConfirmedRequests(Long eventId);

    @Modifying
    @Transactional
    @Query("UPDATE Request AS r " +
            "SET r.status = 'REJECTED' " +
            "WHERE r.status = 'PENDING' " +
            "AND r.eventId = ?1")
    void rejectPendingRequests(Long eventId);
}