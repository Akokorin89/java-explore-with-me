package com.example.ewmmainservice.compilation.repository;

import com.example.ewmmainservice.compilation.model.Compilation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query("SELECT c FROM Compilation AS c " +
            "WHERE :pinned IS NULL OR c.pinned = :pinned")
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);

    @Modifying
    @Query("UPDATE Compilation AS c " +
            "SET c.pinned = ?1 " +
            "WHERE c.id = ?2")
    void setCompilationPinned(boolean pinned, Long compId);
}
