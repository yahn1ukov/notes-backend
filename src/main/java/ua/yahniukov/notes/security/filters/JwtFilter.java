package ua.yahniukov.notes.security.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.yahniukov.notes.exceptions.UnauthorizedException;
import ua.yahniukov.notes.security.providers.JwtProvider;
import ua.yahniukov.notes.services.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    @Value("${jwt.token.prefix}")
    private String tokenPrefix;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);

        try {
            if (token != null && jwtProvider.validateToken(token) && token.startsWith(tokenPrefix)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    var user = userService.findByUsername(authentication.getName());
                    request.setAttribute("userId", user.getId());
                }
            }
        } catch (UnauthorizedException ex) {
            SecurityContextHolder.clearContext();
            throw new UnauthorizedException();
        }
        filterChain.doFilter(request, response);
    }
}