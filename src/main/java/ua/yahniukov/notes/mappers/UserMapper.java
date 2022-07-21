package ua.yahniukov.notes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yahniukov.notes.models.dto.UserDto;
import ua.yahniukov.notes.models.entities.UserEntity;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDTO(UserEntity user);

    List<UserDto> toDTOList(List<UserEntity> users);
}