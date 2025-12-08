package ru.itk.mvc_service.json_view.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

  @JsonView(Views.UserSummary.class)
  private Long id;

  @JsonView(Views.UserSummary.class)
  private String name;

  @JsonView(Views.UserSummary.class)
  private String email;

  @JsonView(Views.UserDetails.class)
  private String address;

  @JsonView(Views.UserDetails.class)
  private List<OrderDto> orders;
}
