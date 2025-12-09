package ru.itk.mvc_service.json_view.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.mvc_service.json_view.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}