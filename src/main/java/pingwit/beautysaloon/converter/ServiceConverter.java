package pingwit.beautysaloon.converter;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.ServiceDTO;
import pingwit.beautysaloon.repositiry.model.SaloonService;

import java.util.Collection;
import java.util.List;

@Component
public class ServiceConverter {
    private final ClientConverter clientConverter;
    private final MasterConverter masterConverter;
    private final ProcedureConverter procedureConverter;

    public ServiceConverter(ClientConverter clientConverter, MasterConverter masterConverter, ProcedureConverter procedureConverter) {
        this.clientConverter = clientConverter;
        this.masterConverter = masterConverter;
        this.procedureConverter = procedureConverter;
    }

    public SaloonService convertServiceToEntity(ServiceDTO source) {
        SaloonService result = new SaloonService();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setClient(clientConverter.convertClientToEntity(source.getClient()));
        result.setMaster(masterConverter.convertMasterToEntity(source.getMaster()));
        result.setDate(source.getDate());
        result.setProcedure(procedureConverter.convertProcedureToEntity(source.getProcedure()));
        result.setPrice(source.getPrice());
        return result;
    }

    public ServiceDTO convertServiceToDTO(SaloonService source) {
        return convertToDto(source);
    }

    public List<ServiceDTO> convertServiceToDTO(Collection<SaloonService> source) {
        return source.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ServiceDTO convertToDto(SaloonService source) {
        ServiceDTO result = new ServiceDTO();
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
