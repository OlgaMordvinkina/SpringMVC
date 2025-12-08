package ru.itk.mvc_service.json_view.handler.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String entityName, Number id) {
    super(String.format("%s с ID=%s не существует", entityName, id));
  }
}