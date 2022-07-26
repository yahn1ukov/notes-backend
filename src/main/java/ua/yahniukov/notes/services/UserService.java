package ua.yahniukov.notes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.yahniukov.notes.data.UpdatePasswordRequest;
import ua.yahniukov.notes.enums.UserRole;
import ua.yahniukov.notes.exceptions.*;
import ua.yahniukov.notes.models.dto.UserDto;
import ua.yahniukov.notes.models.entities.UserEntity;
import ua.yahniukov.notes.repositories.UserRepository;
import ua.yahniukov.notes.security.configurations.EncoderConfiguration;
import ua.yahniukov.notes.security.data.RegistrationRequest;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final EncoderConfiguration passwordConfiguration;

    public UserEntity findById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public void create(RegistrationRequest registrationUser) {
        if (userRepository.existsByUsername(registrationUser.username())) {
            throw new UserAlreadyExistsException();
        }

        var createdUser = UserEntity.builder()
                .username(registrationUser.username())
                .password(passwordConfiguration.passwordEncoder().encode(registrationUser.password()))
                .build();

        if (!userRepository.existsByRole(UserRole.ADMIN)) {
            createdUser.setRole(UserRole.ADMIN);
        }

        userRepository.save(createdUser);
    }

    public UserDto get(Long userId) {
        return UserDto.fromUser(findById(userId));
    }

    public List<UserDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    public void updatePassword(Long userId, UpdatePasswordRequest password) {
        var user = findById(userId);
        if (!passwordConfiguration.passwordEncoder().matches(password.oldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }
        if (password.oldPassword().equals(password.newPassword())) {
            throw new SamePasswordException();
        }
        user.setPassword(passwordConfiguration.passwordEncoder().encode(password.newPassword()));
        userRepository.save(user);
    }

    public void ban(Long userId) {
        var user = findById(userId);
        if (user.getRole().equals(UserRole.ADMIN)) {
            throw new AdminIsNotBlockException();
        }
        user.setIsBanned(!user.getIsBanned());
        userRepository.save(user);
    }

    public void delete(Long userId) {
        var user = findById(userId);
        userRepository.delete(user);
    }
}