package ua.yahniukov.notes.models.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.yahniukov.notes.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserEntity extends BaseEntity {
    private String username;

    private String password;

    @Column(name = "is_banned")
    @Builder.Default
    private Boolean isBanned = false;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<NoteEntity> notes = new ArrayList<>();
}