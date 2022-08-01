package ua.yahniukov.notes.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @PostMapping
    @ApiOperation("Create note")
    public void create(
            @ApiParam(hidden = true) @RequestAttribute("userId") Long userId,
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

    @GetMapping
    @ApiOperation("Get list of user's notes")
    public List<NoteDto> getAll(
            @ApiParam(hidden = true) @RequestAttribute("userId") Long userId
    ) {
        return noteService.getAll(userId);
    }

    @PatchMapping("/{noteId}")
    @ApiOperation("Update a note by id")
    public void update(
            @PathVariable("noteId") Long noteId,
            @RequestBody NoteRequest updatedNote
    ) {
        noteService.update(noteId, updatedNote);
    }

    @DeleteMapping("/{noteId}")
    @ApiOperation("Delete a user's note by id")
    public void delete(
            @ApiParam(hidden = true) @RequestAttribute("userId") Long userId,
            @PathVariable("noteId") Long noteId
    ) {
        noteService.delete(userId, noteId);
    }
}