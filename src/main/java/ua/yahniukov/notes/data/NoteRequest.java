package ua.yahniukov.notes.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NoteRequest(
        String title,
        String text
) {
}