package ru.itk.mvc_service.json_view.annotation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itk.mvc_service.json_view.annotation.UniqueEmail;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.service.UserService;
import ru.itk.mvc_service.json_view.utils.HttpRequestUtils;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
  private final static String USER_WITH_EMAIL_ALREADY_EXISTS_MESSAGE = "Пользователь с адресом электронной почты=%s уже существует";

  private final UserService userService;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    Optional<User> user = userService.findByEmail(email);
    if (user.isPresent()) {
      if (!Objects.equals(HttpRequestUtils.findIdFromRequest(), user.get().getId())) {
        addMessage(String.format(USER_WITH_EMAIL_ALREADY_EXISTS_MESSAGE, email), context);
        return false;
      }
    }
    return true;
  }

  protected void addMessage(String message, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    context
      .buildConstraintViolationWithTemplate(message)
      .addConstraintViolation();
  }
}
