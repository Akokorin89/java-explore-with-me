package ru.practicum.ewmmainservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.user.model.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByIdIn(Set<Long> ids);
}
