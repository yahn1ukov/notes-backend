package ua.yahniukov.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yahniukov.notes.enums.UserRole;
import ua.yahniukov.notes.models.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    boolean existsByRole(UserRole role);

    Optional<UserEntity> findByUsername(String username);
}
