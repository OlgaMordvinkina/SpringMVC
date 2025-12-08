package ru.itk.mvc_service.json_view.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.itk.mvc_service.json_view.TestConfig;
import ru.itk.mvc_service.json_view.entity.Order;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.enums.OrderStatus;
import ru.itk.mvc_service.json_view.mapper.OrderMapper;
import ru.itk.mvc_service.json_view.model.SaveOrderDto;
import ru.itk.mvc_service.json_view.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import({TestConfig.class})
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper mapper;
  @MockitoBean
  private OrderService orderService;
  @Autowired
  private OrderMapper orderMapper;

  @Test
  void shouldReturnOrderSummaryList() throws Exception {
    Order order = new Order();
    order.setId(1L);

    when(orderService.findAll()).thenReturn(List.of(order));

    mockMvc.perform(get("/orders/all"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].user").doesNotExist());
  }

  @Test
  void shouldReturnOrderDetails() throws Exception {
    Order order = new Order();
    order.setId(5L);
    order.setStatus(OrderStatus.NEW);
    User user = new User();
    user.setId(10L);
    order.setUser(user);

    when(orderService.getById(5L)).thenReturn(order);

    mockMvc.perform(get("/orders/5"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(5))
      .andExpect(jsonPath("$.status").value("NEW"));
  }

  @Test
  void shouldCreateOrder() throws Exception {
    SaveOrderDto dto = new SaveOrderDto();
    dto.setStatus(OrderStatus.PROCESSING);
    dto.setUserId(1L);
    dto.setAmount(BigDecimal.valueOf(100));

    Order order = new Order();
    order.setId(100L);
    order.setStatus(OrderStatus.PROCESSING);
    order.setAmount(BigDecimal.valueOf(100));
    User user = new User();
    user.setId(1L);
    order.setUser(user);

    when(orderService.create(any(SaveOrderDto.class))).thenReturn(order);

    mockMvc.perform(post("/orders/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(dto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(100))
      .andExpect(jsonPath("$.status").value("PROCESSING"))
      .andExpect(jsonPath("$.amount").value(100));
  }

  @Test
  void shouldDeleteOrder() throws Exception {
    mockMvc.perform(delete("/orders/50"))
      .andExpect(status().isNoContent());
  }
}
