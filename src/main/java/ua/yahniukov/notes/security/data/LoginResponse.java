package ua.yahniukov.notes.security.data;

import ua.yahniukov.notes.enums.UserRole;

public record LoginResponse(
        String token,
        UserRole role
) {
}
