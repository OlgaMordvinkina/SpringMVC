package ru.itk.mvc_service.json_view.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.itk.mvc_service.json_view.model.Views;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.UserSummary.class)
  Long id;

  @NotBlank
  @JsonView(Views.UserSummary.class)
  String name;

  @NotBlank
  @Email
  @JsonView(Views.UserSummary.class)
  @Column(unique = true, nullable = false)
  String email;

  @JsonView(Views.UserDetails.class)
  String address;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonView(Views.UserDetails.class)
  List<Order> orders = new ArrayList<>();
}