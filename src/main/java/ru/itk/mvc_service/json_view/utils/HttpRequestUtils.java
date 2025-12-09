package ru.itk.mvc_service.json_view.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class HttpRequestUtils {

  public static Long findIdFromRequest() {
    return Long.valueOf(findParameterFromRequest("id"));
  }

  public static String findParameterFromRequest(String nameParameter) {
    // Получаем текущий HTTP-запрос
    ServletRequestAttributes attributes = getRequestAttributes();
    if (Objects.isNull(attributes)) {
      throw new IllegalStateException("Запрос не найден. Этот метод должен вызываться в контексте HTTP-запроса.");
    }

    HttpServletRequest request = attributes.getRequest();

    // Извлекаем path variables из запроса
    @SuppressWarnings("unchecked")
    Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

    // Получаем значение id из path variables или request parameters
    return Optional.ofNullable(pathVariables).map(vars -> vars.get(nameParameter)).filter(StringUtils::isNotEmpty)
      .orElseGet(() -> request.getParameter(nameParameter));
  }

  private static ServletRequestAttributes getRequestAttributes() {
    return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
  }

}
