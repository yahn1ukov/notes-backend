package ua.yahniukov.notes.security.data;

public record LoginResponse(
        String token,
        String role
) {
}
