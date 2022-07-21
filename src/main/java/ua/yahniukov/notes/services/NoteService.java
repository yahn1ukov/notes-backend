package ua.yahniukov.notes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.yahniukov.notes.data.NoteRequest;
import ua.yahniukov.notes.exceptions.NoteNotFoundException;
import ua.yahniukov.notes.mappers.NoteMapper;
import ua.yahniukov.notes.models.dto.NoteDto;
import ua.yahniukov.notes.models.entities.NoteEntity;
import ua.yahniukov.notes.repositories.NoteRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteEntity findById(Long noteId) {
        return noteRepository
                .findById(noteId)
                .orElseThrow(NoteNotFoundException::new);
    }

    public void create(Long userId, NoteRequest note) {
        var user = userService.findById(userId);
        noteRepository.save(
                NoteEntity.builder()
                        .title(note.title())
                        .text(note.text())
                        .user(user)
                        .build()
        );
    }

    public NoteDto get(Long noteId) {
        var note = findById(noteId);
        return NoteMapper.INSTANCE.toDTO(note);
    }

    public List<NoteDto> getAll(Long userId) {
        var user = userService.findById(userId);
        var notes = noteRepository.findAllByUser(user);
        return NoteMapper.INSTANCE.toDTOList(notes);
    }

    public void update(Long noteId, NoteRequest updatedNote) {
        var note = findById(noteId);
        NoteMapper.INSTANCE.updateNoteFromRequest(updatedNote, note);
        noteRepository.save(note);
    }

    public List<NoteDto> searchByTitle(Long userId, String title) {
        var user = userService.findById(userId);
        var notes = noteRepository.findAllByUserAndTitle(user, title);
        return NoteMapper.INSTANCE.toDTOList(notes);
    }

    public void delete(Long userId, Long noteId) {
        var user = userService.findById(userId);
        var note = findById(noteId);
        user.getNotes().remove(note);
        noteRepository.delete(note);
    }
}