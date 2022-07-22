package ua.yahniukov.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yahniukov.notes.models.entities.NoteEntity;
import ua.yahniukov.notes.models.entities.UserEntity;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findAllByUser(UserEntity user);
}
