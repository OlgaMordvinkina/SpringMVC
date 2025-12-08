package ru.itk.mvc_service.json_view.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itk.mvc_service.json_view.entity.Order;
import ru.itk.mvc_service.json_view.mapper.OrderMapper;
import ru.itk.mvc_service.json_view.model.OrderDto;
import ru.itk.mvc_service.json_view.model.SaveOrderDto;
import ru.itk.mvc_service.json_view.model.Views;
import ru.itk.mvc_service.json_view.service.OrderService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Validated
@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService service;
  private final OrderMapper mapper;

  @PostMapping("create")
  @ResponseStatus(HttpStatus.CREATED)
  @JsonView(Views.OrderDetails.class)
  public OrderDto create(@Valid @RequestBody SaveOrderDto order) {
    log.debug("Request for POST create Order started");
    return mapper.toDto(service.create(order));
  }

  @PutMapping("update/{id}")
  @JsonView(Views.OrderDetails.class)
  public OrderDto update(@PathVariable Long id,
                         @Valid @RequestBody SaveOrderDto order) {
    log.debug("Request for PUT update Order started");
    return mapper.toDto(service.update(id, order));
  }

  @GetMapping("/{id}")
  @JsonView(Views.OrderDetails.class)
  public OrderDto getById(@PathVariable Long id) {
    log.debug("Request for GET Order by id started");
    return mapper.toDto(service.getById(id));
  }

  @GetMapping("all")
  @JsonView(Views.OrderSummary.class)
  public List<OrderDto> getAll(@RequestParam(required = false) Long userId) {
    log.debug("Request for GET all Orders started");
    List<Order> orders = Objects.isNull(userId) ? service.findAll() : service.getAllByUserId(userId);
    return orders.stream()
      .map(mapper::toDto)
      .toList();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    log.debug("Request for DELETE get by id Order started");
    service.deleteById(id);
  }
}