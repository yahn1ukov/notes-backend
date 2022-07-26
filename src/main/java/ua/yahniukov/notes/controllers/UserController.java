package ua.yahniukov.notes.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.yahniukov.notes.data.UpdatePasswordRequest;
import ua.yahniukov.notes.models.dto.UserDto;
import ua.yahniukov.notes.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ApiOperation("Get a user by id")
    public UserDto get(
            @ApiParam(hidden = true) @RequestAttribute("userId") Long userId
    ) {
        return userService.get(userId);
    }

    @PatchMapping("/password")
    @ApiOperation("Change a user's password by id")
    public void updatePassword(
            @ApiParam(hidden = true) @RequestAttribute("userId") Long userId,
            @RequestBody UpdatePasswordRequest password
    ) {
        userService.updatePassword(userId, password);
    }

    @DeleteMapping
    @ApiOperation("Delete a user by id")
    public void delete(
            @ApiParam(hidden = true) @RequestAttribute("userId") Long userId
    ) {
        userService.delete(userId);
    }
}