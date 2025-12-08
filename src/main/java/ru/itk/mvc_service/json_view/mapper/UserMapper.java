package ru.itk.mvc_service.json_view.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.itk.mvc_service.json_view.entity.User;
import ru.itk.mvc_service.json_view.model.SaveUserDto;
import ru.itk.mvc_service.json_view.model.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toEntity(SaveUserDto entity);

  User toUpdateEntity(SaveUserDto user, @MappingTarget User updatedUser);

  UserDto toDto(User entity);
}
