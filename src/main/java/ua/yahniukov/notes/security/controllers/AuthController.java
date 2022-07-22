package ua.yahniukov.notes.security.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yahniukov.notes.security.data.LoginRequest;
import ua.yahniukov.notes.security.data.LoginResponse;
import ua.yahniukov.notes.security.data.RegistrationRequest;
import ua.yahniukov.notes.security.services.AuthService;
import ua.yahniukov.notes.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation("Login a user")
    public LoginResponse login(
            @RequestBody LoginRequest login
    ) {
        return authService.login(login);
    }

    @PostMapping("/registration")
    @ApiOperation("Registration a user")
    public void registration(
            @RequestBody RegistrationRequest registration
    ) {
        userService.create(registration);
    }

    @PostMapping("/logout")
    @ApiOperation("Logout a user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }
}