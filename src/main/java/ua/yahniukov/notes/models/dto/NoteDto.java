package ua.yahniukov.notes.models.dto;

import java.util.Date;

public record NoteDto(
        Long id,
        String title,
        String text,
        Date createdAt
) {
}