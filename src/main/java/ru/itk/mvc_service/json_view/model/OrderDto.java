package ru.itk.mvc_service.json_view.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

  @JsonView(Views.OrderSummary.class)
  private Long id;

  @JsonView(Views.OrderSummary.class)
  private String status;

  @JsonView(Views.OrderDetails.class)
  private BigDecimal amount;

  @JsonView(Views.OrderDetails.class)
  private List<String> items;
}
