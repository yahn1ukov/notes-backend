package ua.yahniukov.notes.security.data;

public record LoginRequest(
        String username,
        String password
) {
}
