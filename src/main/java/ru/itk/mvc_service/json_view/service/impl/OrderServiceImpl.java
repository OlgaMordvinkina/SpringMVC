package ru.itk.mvc_service.json_view.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.mvc_service.json_view.entity.Order;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.handler.exception.ResourceNotFoundException;
import ru.itk.mvc_service.json_view.mapper.OrderMapper;
import ru.itk.mvc_service.json_view.model.SaveOrderDto;
import ru.itk.mvc_service.json_view.repository.OrderRepository;
import ru.itk.mvc_service.json_view.service.OrderService;
import ru.itk.mvc_service.json_view.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository repository;
  private final UserService userService;
  private final OrderMapper mapper;

  @Override
  @Transactional
  public Order create(SaveOrderDto order) {
    User user = userService.getById(order.getUserId());
    Order newOrder = mapper.toEntity(order, user);
    return repository.save(newOrder);
  }

  @Override
  @Transactional
  public Order update(Long id, SaveOrderDto order) {
    Order existing = getById(id);

    mapper.toUpdateEntity(order, existing);

    return repository.save(existing);
  }

  @Override
  @Transactional(readOnly = true)
  public Order getById(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Заказ", id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> getAllByUserId(Long userId) {
    return repository.findAllByUserId(userId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

}