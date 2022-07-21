package ua.yahniukov.notes.security.data;

public record RegistrationRequest(
        String username,
        String password
) {
}
