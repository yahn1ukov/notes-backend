package ua.yahniukov.notes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.yahniukov.notes.data.UpdatePasswordRequest;
import ua.yahniukov.notes.enums.UserRole;
import ua.yahniukov.notes.exceptions.*;
import ua.yahniukov.notes.mappers.UserMapper;
import ua.yahniukov.notes.models.dto.UserDto;
import ua.yahniukov.notes.models.entities.UserEntity;
import ua.yahniukov.notes.repositories.UserRepository;
import ua.yahniukov.notes.security.data.RegistrationRequest;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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

        userRepository.save(
                UserEntity.builder()
                        .username(registrationUser.username())
                        .password(passwordEncoder.encode(registrationUser.password()))
                        .build()
        );
    }

    public UserDto get(Long userId) {
        var user = findById(userId);
        return UserMapper.INSTANCE.toDTO(user);
    }

    public List<UserDto> getAll() {
        List<UserEntity> users = userRepository.findAll();
        return UserMapper.INSTANCE.toDTOList(users);
    }

    public void updatePassword(Long userId, UpdatePasswordRequest password) {
        var user = findById(userId);
        if (!passwordEncoder.matches(password.oldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }
        if (password.oldPassword().equals(password.newPassword())) {
            throw new SamePasswordException();
        }
        user.setPassword(passwordEncoder.encode(password.newPassword()));
        userRepository.save(user);
    }

    public void block(Long userId) {
        var user = findById(userId);
        if (user.getRole().equals(UserRole.ADMIN)) {
            throw new AdminIsNotBlockException();
        }
        user.setIsNotBlock(!user.getIsNotBlock());
        userRepository.save(user);
    }

    public void delete(Long userId) {
        var user = findById(userId);
        userRepository.delete(user);
    }
}