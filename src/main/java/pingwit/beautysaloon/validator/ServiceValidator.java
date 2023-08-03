package pingwit.beautysaloon.validator;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.controller.dto.ServiceDTO;
import pingwit.beautysaloon.exception.ValidationException;
import pingwit.beautysaloon.service.MasterService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ServiceValidator {

    private final MasterService masterService;

    public ServiceValidator(MasterService masterService) {
        this.masterService = masterService;
    }

    public void validateService(ServiceDTO serviceDTO) {
        List<String> violations = new ArrayList<>();

        validateName(serviceDTO, violations);
        validateClient(serviceDTO, violations);
        validateMaster(serviceDTO, violations);
        validateDate(serviceDTO, violations);
        validateProcedure(serviceDTO, violations);
        validatePrice(serviceDTO, violations);
        validateCanMasterDoProcedure(serviceDTO, violations);

        if (!violations.isEmpty()) {
            throw new ValidationException("Provided service is invalid!", violations);
        }
    }

    private void validateName(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getName().isBlank()) {
            violations.add("name is blank");
        }
    }

    private void validateClient(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getClient().getId() == null) {
            violations.add("clientId can't be null");
        }
    }

    private void validateMaster(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getMaster().getId() == null) {
            violations.add("masterId can't be null");
        }
    }

    private void validateDate(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getDate() == null) {
            violations.add("date can't be null");
        }
    }

    private void validateProcedure(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getProcedure() == null) {
            violations.add("procedureId can't be null");
        }
    }

    private void validatePrice(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getPrice() == null) {
            violations.add("price is null");
        } else if (serviceDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            violations.add("price must be greater than 0");
        }
    }


    private void validateCanMasterDoProcedure(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getMaster().getId() != null && serviceDTO.getProcedure() != null) {
            MasterDTO masterById = masterService.getMasterById(serviceDTO.getMaster().getId());
            Set<Integer> masterProcedures = masterById.getProcedures()
                    .stream()
                    .map(ProcedureDTO::getId)
                    .collect(Collectors.toSet());
            if (!masterProcedures.contains(serviceDTO.getProcedure().getId())) {
                violations.add(String.format("'%s' master does not do procedure '%d'.", masterById.getName(), serviceDTO.getProcedure().getId()));
            }
        }
    }
}
