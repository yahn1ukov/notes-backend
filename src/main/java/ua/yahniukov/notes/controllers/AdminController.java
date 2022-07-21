package ua.yahniukov.notes.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.yahniukov.notes.models.dto.UserDto;
import ua.yahniukov.notes.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    @ApiOperation("Get list of users")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PatchMapping("/users/{userId}/block")
    @ApiOperation("Block a user by id")
    public void block(
            @PathVariable("userId") Long userId
    ) {
        userService.block(userId);
    }

    @DeleteMapping("/users/{userId}/delete")
    @ApiOperation("Delete a user by id")
    public void delete(
            @PathVariable("userId") Long userId
    ) {
        userService.delete(userId);
    }
}