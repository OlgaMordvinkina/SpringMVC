package ru.itk.mvc_service.json_view.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.itk.mvc_service.json_view.enums.OrderStatus;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveOrderDto {

  @Schema(description = "Сумма заказа")
  @NotNull(message = "{default.valid.notNull}")
  @DecimalMin(value = "0.01", message = "{order.valid.amountMin}")
  BigDecimal amount;

  @Schema(description = "Статус заказа")
  @NotNull(message = "{default.valid.notNull}")
  OrderStatus status;

  @NotNull(message = "{default.valid.notNull}")
  @Schema(description = "ИД пользователя")
  Long userId;
}
