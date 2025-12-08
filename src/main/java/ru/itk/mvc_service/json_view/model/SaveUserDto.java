package ru.itk.mvc_service.json_view.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.itk.mvc_service.json_view.annotation.SecondaryValidGroup;
import ru.itk.mvc_service.json_view.annotation.UniqueEmail;

@Data
@GroupSequence({SaveUserDto.class, SecondaryValidGroup.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveUserDto {

  @NotBlank(message = "{default.valid.notBlank}")
  @Schema(description = "Имя пользователя")
  String name;

  @NotBlank(message = "{default.valid.notBlank}")
  @UniqueEmail(groups = SecondaryValidGroup.class)
  @Schema(description = "Почтовый адрес пользователя")
  String email;

  @NotBlank(message = "{default.valid.notBlank}")
  @Schema(description = "Адрес пользователя")
  String address;
}
