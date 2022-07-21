package ua.yahniukov.notes.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ua.yahniukov.notes.data.NoteRequest;
import ua.yahniukov.notes.models.dto.NoteDto;
import ua.yahniukov.notes.models.entities.NoteEntity;

import java.util.List;

@Mapper
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    NoteDto toDTO(NoteEntity note);

    List<NoteDto> toDTOList(List<NoteEntity> notes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNoteFromRequest(NoteRequest updatedNote, @MappingTarget NoteEntity note);
}