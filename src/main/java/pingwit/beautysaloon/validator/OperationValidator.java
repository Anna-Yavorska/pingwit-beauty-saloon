package pingwit.beautysaloon.validator;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.controller.dto.OperationDTO;
import pingwit.beautysaloon.exception.ValidationException;
import pingwit.beautysaloon.service.MasterService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OperationValidator {
    private final MasterService masterService;

    public OperationValidator(MasterService masterService) {
        this.masterService = masterService;
    }

    public void validateOperation(OperationDTO operationDTO) {
        List<String> violations = new ArrayList<>();
        validateName(operationDTO, violations);
        validateClientIsNotNull(operationDTO, violations);
        validateMasterIsNotNull(operationDTO, violations);
        validateDateIsNotNull(operationDTO, violations);
        validateProcedureIsNotNull(operationDTO, violations);
        validatePrice(operationDTO, violations);
        validateCanMasterDoProcedure(operationDTO, violations);

        if (!violations.isEmpty()) {
            throw new ValidationException("Provided operation is invalid!", violations);
        }
    }

    private void validateName(OperationDTO operationDTO, List<String> violations) {
        if (operationDTO.getName().isBlank()) {
            violations.add("name is blank");
        }
    }

    private void validateClientIsNotNull(OperationDTO operationDTO, List<String> violations) {
        if (operationDTO.getClient().getId() == null) {
            violations.add("clientId can't be null");
        }
    }

    private void validateMasterIsNotNull(OperationDTO operationDTO, List<String> violations) {
        if (operationDTO.getMaster().getId() == null) {
            violations.add("masterId can't be null");
        }
    }

    private void validateDateIsNotNull(OperationDTO operationDTO, List<String> violations) {
        if (operationDTO.getDate() == null) {
            violations.add("date can't be null");
        }
    }

    private void validateProcedureIsNotNull(OperationDTO operationDTO, List<String> violations) {
        if (operationDTO.getProcedure() == null) {
            violations.add("procedureId can't be null");
        }
    }

    private void validatePrice(OperationDTO operationDTO, List<String> violations) {
        if (operationDTO.getPrice() == null) {
            violations.add("price is null");
        } else if (operationDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            violations.add("price must be greater than 0");
        }
    }

    private void validateCanMasterDoProcedure(OperationDTO operationDTO, List<String> violations) {
        if (operationDTO.getMaster().getId() != null && operationDTO.getProcedure() != null) {
            MasterDTO masterById = masterService.getMasterById(operationDTO.getMaster().getId());
            Set<Integer> masterProcedures = masterById.getProcedures()
                    .stream()
                    .map(ProcedureDTO::getId)
                    .collect(Collectors.toSet());
            if (!masterProcedures.contains(operationDTO.getProcedure().getId())) {
                violations.add(String.format("'%s' master does not do procedure '%d'.", masterById.getName(), operationDTO.getProcedure().getId()));
            }
        }
    }
}
