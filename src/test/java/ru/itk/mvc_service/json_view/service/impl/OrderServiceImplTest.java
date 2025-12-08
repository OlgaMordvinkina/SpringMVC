package ru.itk.mvc_service.json_view.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itk.mvc_service.json_view.entity.Order;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.enums.OrderStatus;
import ru.itk.mvc_service.json_view.mapper.OrderMapperImpl;
import ru.itk.mvc_service.json_view.mapper.UserMapperImpl;
import ru.itk.mvc_service.json_view.model.SaveOrderDto;
import ru.itk.mvc_service.json_view.repository.OrderRepository;
import ru.itk.mvc_service.json_view.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private UserRepository userRepository;
  private OrderServiceImpl orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    UserServiceImpl userService = new UserServiceImpl(userRepository, new UserMapperImpl());
    orderService = new OrderServiceImpl(orderRepository, userService, new OrderMapperImpl());
  }

  @Test
  void shouldCreateOrder() {
    SaveOrderDto dto = new SaveOrderDto();
    dto.setStatus(OrderStatus.NEW);
    dto.setUserId(1L);

    User user = new User();
    user.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
      Order o = invocation.getArgument(0);
      o.setId(10L);
      if (o.getUser() == null) {
        o.setUser(user);
      }
      return o;
    });

    Order result = orderService.create(dto);

    assertNotNull(result);
    assertEquals(10L, result.getId());
    assertEquals(OrderStatus.NEW, result.getStatus());
    assertNotNull(result.getUser());
    assertEquals(1L, result.getUser().getId());
    verify(orderRepository).save(any(Order.class));
  }

  @Test
  void shouldUpdateOrder() {
    SaveOrderDto dto = new SaveOrderDto();
    dto.setStatus(OrderStatus.PROCESSING);
    dto.setUserId(1L);

    Order existing = new Order();
    existing.setId(5L);
    existing.setStatus(OrderStatus.NEW);
    existing.setUser(new User());
    existing.getUser().setId(1L);

    when(orderRepository.findById(5L)).thenReturn(Optional.of(existing));
    when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Order result = orderService.update(5L, dto);

    assertNotNull(result);
    assertEquals(OrderStatus.PROCESSING, result.getStatus());
    assertEquals(1L, result.getUser().getId());
  }

  @Test
  void shouldGetOrderById() {
    Order order = new Order();
    order.setId(2L);

    when(orderRepository.findById(2L)).thenReturn(Optional.of(order));

    Order result = orderService.getById(2L);
    assertEquals(2L, result.getId());
  }

  @Test
  void shouldFindAllOrders() {
    Order order = new Order();
    order.setId(3L);

    when(orderRepository.findAll()).thenReturn(List.of(order));

    List<Order> result = orderService.findAll();
    assertEquals(1, result.size());
    assertEquals(3L, result.get(0).getId());
  }

  @Test
  void shouldGetAllByUserId() {
    Order order = new Order();
    order.setId(4L);
    order.setUser(new User());
    order.getUser().setId(1L);

    when(orderRepository.findAllByUserId(1L)).thenReturn(List.of(order));

    List<Order> result = orderService.getAllByUserId(1L);
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getUser().getId());
  }

  @Test
  void shouldDeleteOrder() {
    doNothing().when(orderRepository).deleteById(5L);

    orderService.deleteById(5L);

    verify(orderRepository).deleteById(5L);
  }
}
