package ru.itk.mvc_service.json_view;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.itk.mvc_service.json_view.mapper.OrderMapper;
import ru.itk.mvc_service.json_view.mapper.UserMapper;

@TestConfiguration
public class TestConfig {
  @Bean
  public UserMapper userMapper() {
    return Mappers.getMapper(UserMapper.class);
  }

  @Bean
  public OrderMapper orderMapper() {
    return Mappers.getMapper(OrderMapper.class);
  }
}
