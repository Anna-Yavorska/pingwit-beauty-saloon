package pingwit.beautysaloon.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProcedureValidatorTest {
    private final ProcedureValidator target = new ProcedureValidator();

    @Test
    @DisplayName("Validation Error should not be thrown when input procedure is valid")
    void shouldNotThrow_whenProcedureIsValid() {
        //given
        ProcedureDTO valid = validProcedure();

        //when
        assertDoesNotThrow(() -> target.validateProcedure(valid));

        //then

    }

    @Test
    @DisplayName("Validation Error should be thrown when name is invalid")
    void shouldThrow_whenNameIsBlank() {
        //given
        ProcedureDTO blankNameProcedure = blankNameProcedure();
        String expectedMessage = "name is blank";

        //when
        BeautySalonValidationException validationException = assertThrows(BeautySalonValidationException.class, () -> target.validateProcedure(blankNameProcedure));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when time is null")
    void shouldThrow_whenTimeIsNull() {
        //given
        ProcedureDTO timeOfProcedureIsNull = timeOfProcedureIsNull();
        String expectedMessage = "time is null";

        //when
        BeautySalonValidationException validationException = assertThrows(BeautySalonValidationException.class, () -> target.validateProcedure(timeOfProcedureIsNull));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when time is invalid")
    void shouldThrow_whenTimeIsInvalid() {
        //given
        ProcedureDTO timeOfProcedureIsInvalid = timeOfProcedureIsInvalid();
        String expectedMessage = "time must be greater than 0";

        //when
        BeautySalonValidationException validationException = assertThrows(BeautySalonValidationException.class, () -> target.validateProcedure(timeOfProcedureIsInvalid));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    private ProcedureDTO validProcedure() {
        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("TestName");
        procedure.setDescription("TestDescription");
        procedure.setTime(new BigDecimal("0.5"));
        return procedure;
    }

    private ProcedureDTO blankNameProcedure() {
        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("");
        procedure.setDescription("TestDescription");
        procedure.setTime(new BigDecimal("0.5"));
        return procedure;
    }

    private ProcedureDTO timeOfProcedureIsNull() {
        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("TestName");
        procedure.setDescription("TestDescription");
        procedure.setTime(null);
        return procedure;
    }

    private ProcedureDTO timeOfProcedureIsInvalid() {
        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("TestName");
        procedure.setDescription("TestDescription");
        procedure.setTime(new BigDecimal("0"));
        return procedure;
    }
}