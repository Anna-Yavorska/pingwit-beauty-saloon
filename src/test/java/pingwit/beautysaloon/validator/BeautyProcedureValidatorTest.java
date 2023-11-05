package pingwit.beautysaloon.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeautyProcedureValidatorTest {
    private final BeautyProcedureValidator target = new BeautyProcedureValidator();

    @Test
    @DisplayName("Validation Error should not be thrown when input procedure is valid")
    void shouldNotThrow_whenProcedureIsValid() {
        //given
        BeautyProcedureDTO valid = validProcedure();

        //when
        assertDoesNotThrow(() -> target.validateProcedure(valid));

        //then
    }

    @Test
    @DisplayName("Validation Error should be thrown when name is invalid")
    void shouldThrow_whenNameIsBlank() {
        //given
        BeautyProcedureDTO blankNameProcedure = blankNameProcedure();
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
        BeautyProcedureDTO timeOfProcedureIsNull = timeOfProcedureIsNull();
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
        BeautyProcedureDTO timeOfProcedureIsInvalid = timeOfProcedureIsInvalid();
        String expectedMessage = "time must be greater than 0";

        //when
        BeautySalonValidationException validationException = assertThrows(BeautySalonValidationException.class, () -> target.validateProcedure(timeOfProcedureIsInvalid));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    private BeautyProcedureDTO validProcedure() {
        BeautyProcedureDTO procedure = new BeautyProcedureDTO();
        procedure.setName("TestName");
        procedure.setDescription("TestDescription");
        procedure.setTime(new BigDecimal("0.5"));
        return procedure;
    }

    private BeautyProcedureDTO blankNameProcedure() {
        BeautyProcedureDTO procedure = new BeautyProcedureDTO();
        procedure.setName("");
        procedure.setDescription("TestDescription");
        procedure.setTime(new BigDecimal("0.5"));
        return procedure;
    }

    private BeautyProcedureDTO timeOfProcedureIsNull() {
        BeautyProcedureDTO procedure = new BeautyProcedureDTO();
        procedure.setName("TestName");
        procedure.setDescription("TestDescription");
        procedure.setTime(null);
        return procedure;
    }

    private BeautyProcedureDTO timeOfProcedureIsInvalid() {
        BeautyProcedureDTO procedure = new BeautyProcedureDTO();
        procedure.setName("TestName");
        procedure.setDescription("TestDescription");
        procedure.setTime(new BigDecimal("0"));
        return procedure;
    }
}