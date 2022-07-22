package ua.yahniukov.notes.security.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegistrationRequest(
        String username,
        String password
) {
}
