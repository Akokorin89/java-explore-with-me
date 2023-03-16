package ru.practicum.ewmmainservice.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
