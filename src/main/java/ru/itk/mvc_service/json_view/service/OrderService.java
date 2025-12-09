package ru.itk.mvc_service.json_view.service;

import ru.itk.mvc_service.json_view.entity.Order;
import ru.itk.mvc_service.json_view.model.SaveOrderDto;

import java.util.List;

public interface OrderService {
  Order create(SaveOrderDto order);

  Order update(Long id, SaveOrderDto order);

  Order getById(Long id);

  List<Order> findAllOrByUserId(Long userId);

  List<Order> getAllByUserId(Long id);

  List<Order> findAll();

  void deleteById(Long id);
}
