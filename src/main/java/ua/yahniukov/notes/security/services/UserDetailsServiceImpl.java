package ua.yahniukov.notes.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.yahniukov.notes.services.UserService;

import java.util.Set;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username);
        return new User(
                user.getUsername(),
                user.getPassword(),
                Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
