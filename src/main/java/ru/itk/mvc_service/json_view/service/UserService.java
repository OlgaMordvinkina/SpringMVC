package ru.itk.mvc_service.json_view.service;

import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.model.SaveUserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
  User create(SaveUserDto user);

  User update(Long id, SaveUserDto user);

  User getById(Long id);

  Optional<User> findByEmail(String email);

  List<User> findAll();

  void deleteById(Long id);
}
