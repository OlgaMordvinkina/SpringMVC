package ru.itk.mvc_service.json_view.service;

import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.model.SaveUserDto;

import java.util.List;

public interface UserService {
  User create(SaveUserDto user);

  User update(Long id, SaveUserDto user);

  User getById(Long id);

  boolean existsByEmail(String email);

  List<User> findAll();

  void deleteById(Long id);
}
