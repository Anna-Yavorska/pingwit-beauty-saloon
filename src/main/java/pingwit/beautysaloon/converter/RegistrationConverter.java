package pingwit.beautysaloon.converter;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.RegistrationDTO;
import pingwit.beautysaloon.repository.model.Registration;

import java.util.Collection;
import java.util.List;

@Component
public class RegistrationConverter {
    private final ClientConverter clientConverter;
    private final MasterConverter masterConverter;
    private final BeautyProcedureConverter beautyProcedureConverter;

    public RegistrationConverter(ClientConverter clientConverter, MasterConverter masterConverter, BeautyProcedureConverter beautyProcedureConverter) {
        this.clientConverter = clientConverter;
        this.masterConverter = masterConverter;
        this.beautyProcedureConverter = beautyProcedureConverter;
    }

    public Registration convertOperationToEntity(RegistrationDTO source) {
        Registration result = new Registration();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setClient(clientConverter.convertClientToEntity(source.getClient()));
        result.setMaster(masterConverter.convertMasterToEntity(source.getMaster()));
        result.setDate(source.getDate());
        result.setProcedure(beautyProcedureConverter.convertProcedureToEntity(source.getProcedure()));
        result.setPrice(source.getPrice());
        return result;
    }

    public RegistrationDTO convertOperationToDTO(Registration source) {
        return convertToDto(source);
    }

    public List<RegistrationDTO> convertOperationToDTO(Collection<Registration> source) {
        return source.stream()
                .map(this::convertToDto)
                .toList();
    }

    private RegistrationDTO convertToDto(Registration source) {
        RegistrationDTO result = new RegistrationDTO();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setClient(clientConverter.convertClientToDTO(source.getClient()));
        result.setMaster(masterConverter.convertMasterToDTO(source.getMaster()));
        result.setDate(source.getDate());
        result.setProcedure(beautyProcedureConverter.convertProcedureToDTO(source.getProcedure()));
        result.setPrice(source.getPrice());
        return result;
    }
}
