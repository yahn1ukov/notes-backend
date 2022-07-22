package ua.yahniukov.notes.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdatePasswordRequest(
        String oldPassword,
        String newPassword
) {
}