package ru.itk.mvc_service.json_view.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.mapper.UserMapperImpl;
import ru.itk.mvc_service.json_view.model.SaveUserDto;
import ru.itk.mvc_service.json_view.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

  @Mock
  private UserRepository repository;
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userService = new UserServiceImpl(repository, new UserMapperImpl());
  }

  @Test
  void shouldCreateUser() {
    SaveUserDto dto = new SaveUserDto();
    dto.setName("A");
    dto.setEmail("a@mail.com");
    dto.setAddress("addr");

    when(repository.save(any(User.class))).thenAnswer(invocation -> {
      User u = invocation.getArgument(0);
      u.setId(1L);
      return u;
    });

    User result = userService.create(dto);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("A", result.getName());
    assertEquals("a@mail.com", result.getEmail());
    assertEquals("addr", result.getAddress());
    verify(repository).save(any(User.class));
  }

  @Test
  void shouldUpdateUser() {
    SaveUserDto dto = new SaveUserDto();
    dto.setName("Updated");
    dto.setEmail("upd@mail.com");
    dto.setAddress("addr2");

    User existing = new User();
    existing.setId(1L);
    existing.setName("Old");
    existing.setEmail("old@mail.com");
    existing.setAddress("addr");

    when(repository.findById(1L)).thenReturn(Optional.of(existing));
    when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User result = userService.update(1L, dto);

    assertNotNull(result);
    assertEquals("Updated", result.getName());
    assertEquals("upd@mail.com", result.getEmail());
    assertEquals("addr2", result.getAddress());
    verify(repository).findById(1L);
    verify(repository).save(existing);
  }

  @Test
  void shouldGetUserById() {
    User user = new User();
    user.setId(10L);
    user.setName("B");

    when(repository.findById(10L)).thenReturn(Optional.of(user));

    User result = userService.getById(10L);

    assertNotNull(result);
    assertEquals("B", result.getName());
  }

  @Test
  void shouldThrowWhenUserNotFound() {
    when(repository.findById(999L)).thenReturn(Optional.empty());

    RuntimeException ex = assertThrows(RuntimeException.class,
      () -> userService.getById(999L));
    assertTrue(ex.getMessage().contains("Пользователь"));
  }

  @Test
  void shouldFindAllUsers() {
    User u1 = new User();
    u1.setId(1L);
    u1.setName("A");
    User u2 = new User();
    u2.setId(2L);
    u2.setName("B");

    when(repository.findAll()).thenReturn(List.of(u1, u2));

    List<User> result = userService.findAll();

    assertEquals(2, result.size());
  }


  @Test
  void shouldDeleteUser() {
    doNothing().when(repository).deleteById(1L);

    userService.deleteById(1L);

    verify(repository).deleteById(1L);
  }
}
