package ua.yahniukov.notes.models.dto;

import lombok.Builder;
import ua.yahniukov.notes.models.entities.NoteEntity;

import java.util.Date;

@Builder
public record NoteDto(
        Long id,
        String title,
        String text,
        Date createdAt
) {
    public static NoteDto fromNote(NoteEntity note) {
        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .text(note.getText())
                .createdAt(note.getCreatedAt())
                .build();
    }
}