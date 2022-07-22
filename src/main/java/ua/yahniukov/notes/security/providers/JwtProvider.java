package ua.yahniukov.notes.security.providers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.yahniukov.notes.exceptions.UnauthorizedException;
import ua.yahniukov.notes.security.services.AuthService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final AuthService authService;
    @Value("${jwt.token.header}")
    private String authorizationHeader;
    @Value("${jwt.token.prefix}")
    private String tokenPrefix;
    @Value("${jwt.token.secret}")
    private String tokenSecretKey;
    @Value("${jwt.token.expiration}")
    private Long tokenValidityInMilliseconds;
    @Value("${jwt.token.type}")
    private String tokenType;

    @PostConstruct
    protected void init() {
        tokenSecretKey = Base64.getEncoder().encodeToString(tokenSecretKey.getBytes());
    }

    public String createToken(String username) {
        var claims = Jwts.claims().setSubject(username);

        var now = new Date();
        var validity = new Date(now.getTime() + tokenValidityInMilliseconds * 1000);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, tokenSecretKey)
                .setHeaderParam("type", tokenType)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public boolean validateToken(String token) throws UnauthorizedException {
        var claimsJws = Jwts.parser()
                .setSigningKey(tokenSecretKey)
                .parseClaimsJws(token.replace(String.format("%s ", tokenPrefix), ""));
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        var user = authService.loadUserByUsername(getUsername(token.replace(String.format("%s ", tokenPrefix), "")));
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts
                .parser()
                .setSigningKey(tokenSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}