package ua.yahniukov.notes.data;

public record UpdatePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
