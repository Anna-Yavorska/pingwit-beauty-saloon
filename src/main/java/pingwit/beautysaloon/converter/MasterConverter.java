package pingwit.beautysaloon.converter;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.repositiry.model.Master;
import pingwit.beautysaloon.repositiry.model.ProfLevel;

import java.util.Collection;
import java.util.List;

@Component
public class MasterConverter {

    private ProcedureConverter procedureConverter;

    public MasterConverter(ProcedureConverter procedureConverter) {
        this.procedureConverter = procedureConverter;
    }

    public Master convertMasterToEntity(MasterDTO source) {
        return new Master(
                source.getId(),
                source.getName(),
                source.getSurname(),
                source.getPhone(),
                ProfLevel.valueOf(source.getProfLevel()),
                source.getProfession()
        );
    }

    public MasterDTO convertMasterToDTO(Master source) {
        return convertToDto(source);
    }

    public List<MasterDTO> convertMasterToDTO(Collection<Master> source) {
        return source.stream()
                .map(this::convertToDto)
                .toList();
    }

    private MasterDTO convertToDto(Master source) {
        MasterDTO result = new MasterDTO();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setSurname(source.getSurname());
        result.setPhone(source.getPhone());
        result.setProfLevel(source.getProfLevel().getValue());
        result.setProfession(source.getProfession());
        result.setProcedures(procedureConverter.convertProcedureToDTO(source.getProcedures()));
        return result;
    }
}
