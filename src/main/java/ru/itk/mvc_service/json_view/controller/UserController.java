package ru.itk.mvc_service.json_view.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itk.mvc_service.json_view.mapper.UserMapper;
import ru.itk.mvc_service.json_view.model.SaveUserDto;
import ru.itk.mvc_service.json_view.model.UserDto;
import ru.itk.mvc_service.json_view.model.Views;
import ru.itk.mvc_service.json_view.service.UserService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper mapper;

  @PostMapping("create")
  @ResponseStatus(HttpStatus.CREATED)
  @JsonView(Views.UserDetails.class)
  public UserDto create(@Valid @RequestBody SaveUserDto user) {
    log.debug("Request for POST create User started");
    return mapper.toDto(userService.create(user));
  }

  @PutMapping("update/{id}")
  @JsonView(Views.UserDetails.class)
  public UserDto update(@PathVariable Long id,
                        @Valid @RequestBody SaveUserDto user) {
    log.debug("Request for PUT update User started");
    return mapper.toDto(userService.update(id, user));
  }

  @GetMapping("/{id}")
  @JsonView(Views.UserDetails.class)
  public UserDto getById(@PathVariable Long id) {
    log.debug("Request for GET get by id User started");
    return mapper.toDto(userService.getById(id));
  }

  @GetMapping("all")
  @JsonView(Views.UserSummary.class)
  public List<UserDto> getAll() {
    log.debug("Request for GET get all Users started");
    return userService.findAll().stream()
      .map(mapper::toDto)
      .toList();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    log.debug("Request for DELETE get by id User started");
    userService.deleteById(id);
  }
}