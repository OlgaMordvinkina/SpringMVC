package ru.itk.mvc_service.json_view.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.handler.exception.ResourceNotFoundException;
import ru.itk.mvc_service.json_view.mapper.UserMapper;
import ru.itk.mvc_service.json_view.model.SaveUserDto;
import ru.itk.mvc_service.json_view.repository.UserRepository;
import ru.itk.mvc_service.json_view.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final UserMapper mapper;

  @Override
  @Transactional
  public User create(SaveUserDto user) {
    User newUser = mapper.toEntity(user);
    return repository.save(newUser);
  }

  @Override
  @Transactional
  public User update(Long id, SaveUserDto user) {
    User existUser = getById(id);
    User updateUser = mapper.toUpdateEntity(user, existUser);
    return repository.save(updateUser);
  }

  @Transactional(readOnly = true)
  @Override
  public User getById(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Пользователь", id));
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<User> findByEmail(String email) {
    return repository.findByEmail(email);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}