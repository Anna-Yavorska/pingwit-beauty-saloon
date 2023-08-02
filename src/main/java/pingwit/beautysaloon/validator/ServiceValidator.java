package pingwit.beautysaloon.validator;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.controller.dto.ServiceDTO;
import pingwit.beautysaloon.exception.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ServiceValidator {
    public void validateService(ServiceDTO serviceDTO) {
        List<String> violations = new ArrayList<>();
        validateName(serviceDTO, violations);
        validateClientIsNotNull(serviceDTO, violations);
        validateMasterIsNotNull(serviceDTO, violations);
        validateDateIsNotNull(serviceDTO, violations);
        validateProcedureIsNotNull(serviceDTO, violations);
        validatePrice(serviceDTO, violations);

        validateCanMasterDoProcedure(serviceDTO, violations);

        if (!violations.isEmpty()) {
            throw new ValidationException("Provided service is invalid!", violations);
        }
    }

    private static void validateName(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getName().isBlank()) {
            violations.add("name is blank");
        }
    }

    private static void validateClientIsNotNull(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getClient().getId() == null) {
            violations.add("clientId can't be null");
        }
    }

    private static void validateMasterIsNotNull(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getMaster().getId() == null) {
            violations.add("masterId can't be null");
        }
    }

    private static void validateDateIsNotNull(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getDate() == null) {
            violations.add("date can't be null");
        }
    }

    private static void validateProcedureIsNotNull(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getProcedure() == null) {
            violations.add("procedureId can't be null");
        }
    }

    private static void validatePrice(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getPrice() == null) {
            violations.add("price is null");
        } else if (serviceDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            violations.add("price must be greater than 0");
        }
    }

    private static void validateCanMasterDoProcedure(ServiceDTO serviceDTO, List<String> violations) {
        if (serviceDTO.getMaster().getId() != null && serviceDTO.getProcedure() != null) {
            Collection<ProcedureDTO> procedures = serviceDTO.getMaster().getProcedures();
            for (ProcedureDTO procedure : procedures) {
                if (!procedure.getId().equals(serviceDTO.getProcedure().getId())) {
                    String master = serviceDTO.getMaster().getName();
                    String proceduresId = serviceDTO.getProcedure().getName();
                    violations.add(String.format("'%s' master does not do procedure '%s'.", master, proceduresId));
                }
            }
        }
    }
}
