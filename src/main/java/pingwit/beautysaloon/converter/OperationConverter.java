package pingwit.beautysaloon.converter;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.OperationDTO;
import pingwit.beautysaloon.repository.model.Operation;

import java.util.Collection;
import java.util.List;

@Component
public class OperationConverter {
    private final ClientConverter clientConverter;
    private final MasterConverter masterConverter;
    private final ProcedureConverter procedureConverter;

    public OperationConverter(ClientConverter clientConverter, MasterConverter masterConverter, ProcedureConverter procedureConverter) {
        this.clientConverter = clientConverter;
        this.masterConverter = masterConverter;
        this.procedureConverter = procedureConverter;
    }

    public Operation convertOperationToEntity(OperationDTO source) {
        Operation result = new Operation();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setClient(clientConverter.convertClientToEntity(source.getClient()));
        result.setMaster(masterConverter.convertMasterToEntity(source.getMaster()));
        result.setDate(source.getDate());
        result.setProcedure(procedureConverter.convertProcedureToEntity(source.getProcedure()));
        result.setPrice(source.getPrice());
        return result;
    }

    public OperationDTO convertOperationToDTO(Operation source) {
        return convertToDto(source);
    }

    public List<OperationDTO> convertOperationToDTO(Collection<Operation> source) {
        return source.stream()
                .map(this::convertToDto)
                .toList();
    }

    private OperationDTO convertToDto(Operation source) {
        OperationDTO result = new OperationDTO();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setClient(clientConverter.convertClientToDTO(source.getClient()));
        result.setMaster(masterConverter.convertMasterToDTO(source.getMaster()));
        result.setDate(source.getDate());
        result.setProcedure(procedureConverter.convertProcedureToDTO(source.getProcedure()));
        result.setPrice(source.getPrice());
        return result;
    }
}
