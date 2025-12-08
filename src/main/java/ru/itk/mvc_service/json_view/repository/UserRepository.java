package ru.itk.mvc_service.json_view.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.mvc_service.json_view.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);
}