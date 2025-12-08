package ru.itk.mvc_service.json_view.annotation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itk.mvc_service.json_view.annotation.UniqueEmail;
import ru.itk.mvc_service.json_view.service.UserService;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
  private final static String USER_WITH_EMAIL_ALREADY_EXISTS_MESSAGE = "Пользователь с адресом электронной почты=%s уже существует";

  private final UserService userService;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (userService.existsByEmail(email)) {
      addMessage(String.format(USER_WITH_EMAIL_ALREADY_EXISTS_MESSAGE, email), context);
      return false;
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
