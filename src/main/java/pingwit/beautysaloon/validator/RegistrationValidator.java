package pingwit.beautysaloon.validator;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.controller.dto.RegistrationDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;
import pingwit.beautysaloon.service.MasterService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RegistrationValidator {
    private final MasterService masterService;

    public RegistrationValidator(MasterService masterService) {
        this.masterService = masterService;
    }

    public void validateOperation(RegistrationDTO registrationDTO) {
        List<String> violations = new ArrayList<>();
        validateName(registrationDTO, violations);
        validateClientIsNotNull(registrationDTO, violations);
        validateMasterIsNotNull(registrationDTO, violations);
        validateDateIsNotNull(registrationDTO, violations);
        validateProcedureIsNotNull(registrationDTO, violations);
        validatePrice(registrationDTO, violations);
        validateCanMasterDoProcedure(registrationDTO, violations);

        if (!violations.isEmpty()) {
            throw new BeautySalonValidationException("Provided operation is invalid!", violations);
        }
    }

    private void validateName(RegistrationDTO registrationDTO, List<String> violations) {
        if (registrationDTO.getName().isBlank()) {
            violations.add("name is blank");
        }
    }

    private void validateClientIsNotNull(RegistrationDTO registrationDTO, List<String> violations) {
        if (registrationDTO.getClient().getId() == null) {
            violations.add("clientId can't be null");
        }
    }

    private void validateMasterIsNotNull(RegistrationDTO registrationDTO, List<String> violations) {
        if (registrationDTO.getMaster().getId() == null) {
            violations.add("masterId can't be null");
        }
    }

    private void validateDateIsNotNull(RegistrationDTO registrationDTO, List<String> violations) {
        if (registrationDTO.getDate() == null) {
            violations.add("date can't be null");
        }
    }

    private void validateProcedureIsNotNull(RegistrationDTO registrationDTO, List<String> violations) {
        if (registrationDTO.getProcedure() == null) {
            violations.add("procedureId can't be null");
        }
    }

    private void validatePrice(RegistrationDTO registrationDTO, List<String> violations) {
        if (registrationDTO.getPrice() == null) {
            violations.add("price is null");
        } else if (registrationDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            violations.add("price must be greater than 0");
        }
    }

    private void validateCanMasterDoProcedure(RegistrationDTO registrationDTO, List<String> violations) {
        if (registrationDTO.getMaster().getId() != null && registrationDTO.getProcedure() != null) {
            MasterDTO masterById = masterService.getMasterById(registrationDTO.getMaster().getId());
            Set<Integer> masterProcedures = masterById.getProcedures()
                    .stream()
                    .map(BeautyProcedureDTO::getId)
                    .collect(Collectors.toSet());
            if (!masterProcedures.contains(registrationDTO.getProcedure().getId())) {
                violations.add(String.format("'%s' master does not do procedure '%d'.", masterById.getName(), registrationDTO.getProcedure().getId()));
            }
        }
    }
}
