package ru.itk.mvc_service.json_view.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.itk.mvc_service.json_view.enums.OrderStatus;
import ru.itk.mvc_service.json_view.model.Views;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.OrderSummary.class)
  Long id;

  @NotNull
  @JsonView(Views.OrderSummary.class)
  BigDecimal amount;

  @JsonView(Views.OrderSummary.class)
  @Enumerated(EnumType.STRING)
  OrderStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;
}