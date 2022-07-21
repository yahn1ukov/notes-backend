package ua.yahniukov.notes.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.yahniukov.notes.data.NoteRequest;
import ua.yahniukov.notes.models.dto.NoteDto;
import ua.yahniukov.notes.services.NoteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
@PreAuthorize("hasRole('USER')")
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/users/current")
    @ApiOperation("Create note")
    public void create(
            @RequestAttribute("userId") Long userId,
            @RequestBody NoteRequest note
    ) {
        noteService.create(userId, note);
    }

    @GetMapping("/{noteId}")
    @ApiOperation("Get a note by id")
    public NoteDto get(
            @PathVariable("noteId") Long noteId
    ) {
        return noteService.get(noteId);
    }

    @GetMapping("/users/current")
    @ApiOperation("Get list of user's notes")
    public List<NoteDto> getAll(
            @RequestAttribute("userId") Long userId
    ) {
        return noteService.getAll(userId);
    }

    @GetMapping("/users/current")
    @ApiOperation("Search a user's note by title")
    public List<NoteDto> searchByTitle(
            @RequestAttribute("userId") Long userId,
            @RequestParam("title") String title
    ) {
        return noteService.searchByTitle(userId, title);
    }

    @PatchMapping("/{noteId}")
    public void update(
            @PathVariable("noteId") Long noteId,
            @RequestBody NoteRequest updatedNote
    ) {
        noteService.update(noteId, updatedNote);
    }

    @DeleteMapping("/{noteId}/users/current")
    @ApiOperation("Delete a user's note by id")
    public void delete(
            @RequestAttribute("userId") Long userId,
            @PathVariable("noteId") Long noteId
    ) {
        noteService.delete(userId, noteId);
    }
}