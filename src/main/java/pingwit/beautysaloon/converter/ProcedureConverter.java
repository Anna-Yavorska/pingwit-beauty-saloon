package pingwit.beautysaloon.converter;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.repositiry.model.Procedure;

import java.util.Collection;
import java.util.List;

@Component
public class ProcedureConverter {
    public Procedure convertProcedureToEntity(ProcedureDTO source) {
        return new Procedure(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getTime()
        );
    }

    public ProcedureDTO convertProcedureToDTO(Procedure source) {
        return convertToDto(source);
    }

    public List<ProcedureDTO> convertProcedureToDTO(Collection<Procedure> source) {
        return source.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ProcedureDTO convertToDto(Procedure source) {
        ProcedureDTO result = new ProcedureDTO();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        result.setTime(source.getTime());
        return result;
    }
}
