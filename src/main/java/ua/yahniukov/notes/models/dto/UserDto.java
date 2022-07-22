package ua.yahniukov.notes.models.dto;

import lombok.Builder;
import ua.yahniukov.notes.enums.UserRole;
import ua.yahniukov.notes.models.entities.UserEntity;

import java.util.Date;

@Builder
public record UserDto(
        Long id,
        String username,
        UserRole role,
        Boolean isBanned,
        Date createdAt
) {
    public static UserDto fromUser(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .isBanned(user.getIsBanned())
                .createdAt(user.getCreatedAt())
                .build();
    }
}