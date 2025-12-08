package ru.itk.mvc_service.json_view.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.mvc_service.json_view.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUserId(Long userId);
}