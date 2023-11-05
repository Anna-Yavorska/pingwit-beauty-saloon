package pingwit.beautysaloon.validator;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeautyProcedureValidator {
    public void validateProcedure(BeautyProcedureDTO beautyProcedureDTO) {
        List<String> violations = new ArrayList<>();
        validateNameField(beautyProcedureDTO, violations);
        validateTimeOfProcedure(beautyProcedureDTO, violations);
        if (!violations.isEmpty()) {
            throw new BeautySalonValidationException("Provided procedure is invalid!", violations);
        }
    }

    private void validateNameField(BeautyProcedureDTO beautyProcedureDTO, List<String> violations) {
        if (beautyProcedureDTO.getName().isBlank()) {
            violations.add("name is blank");
        }
    }

    private void validateTimeOfProcedure(BeautyProcedureDTO beautyProcedureDTO, List<String> violations) {
        if (beautyProcedureDTO.getTime() == null) {
            violations.add("time is null");
        } else if (beautyProcedureDTO.getTime().compareTo(BigDecimal.ZERO) <= 0) {
            violations.add("time must be greater than 0");
        }
    }
}
