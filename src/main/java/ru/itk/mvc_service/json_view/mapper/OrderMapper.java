package ru.itk.mvc_service.json_view.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.itk.mvc_service.json_view.entity.Order;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.model.OrderDto;
import ru.itk.mvc_service.json_view.model.SaveOrderDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  Order toEntity(SaveOrderDto entity, User user);

  Order toUpdateEntity(SaveOrderDto order, @MappingTarget Order updatedOrder);

  OrderDto toDto(Order entity);
}
