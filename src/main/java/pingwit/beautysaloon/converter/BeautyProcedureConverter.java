package pingwit.beautysaloon.converter;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.repository.model.BeautyProcedure;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class BeautyProcedureConverter {
    public BeautyProcedure convertProcedureToEntity(BeautyProcedureDTO source) {
        return convertToEntity(source);
    }

    public List<BeautyProcedure> convertProcedureToEntity(Collection<BeautyProcedureDTO> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream()
                .map(this::convertToEntity)
                .toList();
    }

    public BeautyProcedureDTO convertProcedureToDTO(BeautyProcedure source) {
        return convertToDto(source);
    }

    public List<BeautyProcedureDTO> convertProcedureToDTO(Collection<BeautyProcedure> source) {
        return source.stream()
                .map(this::convertToDto)
                .toList();
    }

    private BeautyProcedure convertToEntity(BeautyProcedureDTO source) {
        BeautyProcedure result = new BeautyProcedure();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        result.setTime(source.getTime());
        return result;
    }

    private BeautyProcedureDTO convertToDto(BeautyProcedure source) {
        BeautyProcedureDTO result = new BeautyProcedureDTO();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        result.setTime(source.getTime());
        return result;
    }
}
