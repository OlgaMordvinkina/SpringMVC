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
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.handler.exception.ResourceNotFoundException;
import ru.itk.mvc_service.json_view.mapper.UserMapper;
import ru.itk.mvc_service.json_view.model.SaveUserDto;
import ru.itk.mvc_service.json_view.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({TestConfig.class})
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper mapper;
  @MockitoBean
  private UserService userService;
  @Autowired
  private UserMapper userMapper;

  @Test
  void shouldReturnUserSummaryList() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("A");
    user.setEmail("a@mail.com");
    user.setAddress("hidden");
    user.setOrders(List.of());

    when(userService.findAll()).thenReturn(List.of(user));

    mockMvc.perform(get("/users/all"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].name").value("A"))
      .andExpect(jsonPath("$[0].email").value("a@mail.com"))
      .andExpect(jsonPath("$[0].address").doesNotExist())
      .andExpect(jsonPath("$[0].orders").doesNotExist());
  }

  @Test
  void shouldReturnUserDetails() throws Exception {
    User u = new User();
    u.setId(5L);
    u.setName("B");
    u.setEmail("b@mail.com");
    u.setAddress("addr");
    u.setOrders(List.of());

    when(userService.getById(5L)).thenReturn(u);

    mockMvc.perform(get("/users/5"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(5))
      .andExpect(jsonPath("$.name").value("B"))
      .andExpect(jsonPath("$.email").value("b@mail.com"))
      .andExpect(jsonPath("$.address").value("addr"))
      .andExpect(jsonPath("$.orders").exists());
  }

  @Test
  void shouldCreateUser() throws Exception {
    SaveUserDto dto = new SaveUserDto();
    dto.setName("Test");
    dto.setEmail("test@mail.com");
    dto.setAddress("address");

    User saved = new User();
    saved.setId(100L);
    saved.setName("Test");
    saved.setEmail("test@mail.com");
    saved.setAddress("address");

    when(userService.create(any(SaveUserDto.class))).thenReturn(saved);

    mockMvc.perform(post("/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(dto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(100))
      .andExpect(jsonPath("$.address").value("address"));
  }

  @Test
  void shouldReturn404WhenUserNotFound() throws Exception {
    long id = 99999L;
    when(userService.getById(id)).thenThrow(new ResourceNotFoundException("Пользователь", id));

    mockMvc.perform(get("/users/99999"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message").exists());
  }

  @Test
  void shouldValidateCreateUser_InvalidEmail() throws Exception {
    SaveUserDto dto = new SaveUserDto();
    dto.setName("Test");
    dto.setEmail("");
    dto.setAddress("addr");

    mockMvc.perform(post("/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(dto)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").exists());
  }

  @Test
  void shouldDeleteUser() throws Exception {
    mockMvc.perform(delete("/users/10"))
      .andExpect(status().isNoContent());
  }
}
