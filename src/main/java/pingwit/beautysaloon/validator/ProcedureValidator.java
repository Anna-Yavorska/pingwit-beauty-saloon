package pingwit.beautysaloon.validator;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.exception.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProcedureValidator {
    public void validateProcedure(ProcedureDTO procedureDTO) {
        List<String> violations = new ArrayList<>();
        validateNameField(procedureDTO, violations);
        validateTimeOfProcedure(procedureDTO, violations);
        if (!violations.isEmpty()) {
            throw new ValidationException("Provided procedure is invalid!", violations);
        }
    }

    private void validateNameField(ProcedureDTO procedureDTO, List<String> violations) {
        if (procedureDTO.getName().isBlank()) {
            violations.add("name is blank");
        }
    }

    private void validateTimeOfProcedure(ProcedureDTO procedureDTO, List<String> violations) {
        if (procedureDTO.getTime() == null) {
            violations.add("time is null");
        } else if (procedureDTO.getTime().compareTo(BigDecimal.ZERO) <= 0) {
            violations.add("time must be greater than 0");
        }
    }
}
