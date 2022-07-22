package ua.yahniukov.notes.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ua.yahniukov.notes.exceptions.IncorrectPasswordException;
import ua.yahniukov.notes.exceptions.UserIsBannedException;
import ua.yahniukov.notes.security.configurations.EncoderConfiguration;
import ua.yahniukov.notes.security.data.LoginRequest;
import ua.yahniukov.notes.security.data.LoginResponse;
import ua.yahniukov.notes.security.providers.JwtProvider;
import ua.yahniukov.notes.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final EncoderConfiguration passwordConfiguration;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));

        var user = userService.findByUsername(login.username());

        if (!passwordConfiguration.passwordEncoder().matches(login.password(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        if (user.getIsBanned()) {
            throw new UserIsBannedException();
        }

        String token = jwtProvider.createToken(user.getUsername());

        return new LoginResponse(token, user.getRole());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}