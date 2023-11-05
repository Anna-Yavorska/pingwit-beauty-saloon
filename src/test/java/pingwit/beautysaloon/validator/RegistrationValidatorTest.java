package pingwit.beautysaloon.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.controller.dto.RegistrationDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;
import pingwit.beautysaloon.service.MasterService;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RegistrationValidatorTest {
    private final MasterService masterService = mock(MasterService.class);
    private final ClientDTO clientDTO = mock(ClientDTO.class);
    private final MasterDTO masterDTO = mock(MasterDTO.class);
    private final BeautyProcedureDTO beautyProcedureDTO = mock(BeautyProcedureDTO.class);
    private final RegistrationValidator target = new RegistrationValidator(masterService);

    @Test
    @DisplayName("Validation Error should not be thrown when input operation is valid")
    void shouldNotThrow_whenOperationIsValid() {
        //given
        RegistrationDTO valid = validOperation();
        when(masterService.getMasterById(valid.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(beautyProcedureDTO));

        //when
        assertDoesNotThrow(() -> target.validateOperation(valid));

        //then
        verify(masterService).getMasterById(valid.getMaster().getId());
        verify(masterDTO).getProcedures();
    }

    @Test
    @DisplayName("Validation Error should be thrown when name is blank")
    void shouldThrow_whenNameIsBlank() {
        //given
        RegistrationDTO blankNameOperation = blackNameOperation();

        String expected = "name is blank";

        when(masterService.getMasterById(blankNameOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(beautyProcedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(blankNameOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when clientId is null")
    void shouldThrow_whenClientIsInvalid() {
        //given
        RegistrationDTO clientInvalidOperation = clientInvalidOperation();
        String expected = "clientId can't be null";

        when(masterService.getMasterById(clientInvalidOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(beautyProcedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(clientInvalidOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when masterId is null")
    void shouldThrow_whenMasterIsInvalid() {
        //given
        RegistrationDTO masterInvalidOperation = masterInvalidOperation();
        String expected = "masterId can't be null";

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(masterInvalidOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when date is null")
    void shouldThrow_whenDateIsInvalid() {
        //given
        RegistrationDTO invalidDateOperation = invalidDateOperation();
        String expected = "date can't be null";

        when(masterService.getMasterById(invalidDateOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(beautyProcedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(invalidDateOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when procedure is null")
    void shouldThrown_whenProcedureIsInvalid() {
        //given
        RegistrationDTO invalidProcedureOperation = invalidProcedureOperation();
        String expected = "procedureId can't be null";

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(invalidProcedureOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when price is null")
    void shouldThrow_whenPriceIsNull() {
        //given
        RegistrationDTO nullPriceOperation = nullPriceOperation();
        String expected = "price is null";

        when(masterService.getMasterById(nullPriceOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(beautyProcedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(nullPriceOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when price is invalid")
    void shouldThrow_whenPriceIsInvalid() {
        //given
        RegistrationDTO invalidPriceOperation = invalidPriceOperation();
        String expected = "price must be greater than 0";

        when(masterService.getMasterById(invalidPriceOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(beautyProcedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(invalidPriceOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when master does not do procedure")
    void shouldThrow_whenMasterDoesNotDoProcedure() {
        //given
        RegistrationDTO invalid = masterInvalidProceduresOperation();
        when(masterService.getMasterById(invalid.getMaster().getId())).thenReturn(masterDTO);

        String expected = String.format("'%s' master does not do procedure '%d'.", masterDTO.getName(), invalid.getProcedure().getId());

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(invalid));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    private RegistrationDTO validOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(beautyProcedureDTO);
        registrationDTO.setPrice(new BigDecimal("15.55"));
        return registrationDTO;
    }

    private RegistrationDTO blackNameOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName(" ");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(beautyProcedureDTO);
        registrationDTO.setPrice(new BigDecimal("15.55"));
        return registrationDTO;
    }

    private RegistrationDTO clientInvalidOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(new ClientDTO());
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(beautyProcedureDTO);
        registrationDTO.setPrice(new BigDecimal("15.55"));
        return registrationDTO;
    }

    private RegistrationDTO masterInvalidOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(new MasterDTO());
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(beautyProcedureDTO);
        registrationDTO.setPrice(new BigDecimal("15.55"));
        return registrationDTO;
    }

    private RegistrationDTO invalidDateOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(null);
        registrationDTO.setProcedure(beautyProcedureDTO);
        registrationDTO.setPrice(new BigDecimal("15.55"));
        return registrationDTO;
    }

    private RegistrationDTO invalidProcedureOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(null);
        registrationDTO.setPrice(new BigDecimal("15.55"));
        return registrationDTO;
    }

    private RegistrationDTO nullPriceOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(beautyProcedureDTO);
        registrationDTO.setPrice(null);
        return registrationDTO;
    }

    private RegistrationDTO invalidPriceOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(beautyProcedureDTO);
        registrationDTO.setPrice(new BigDecimal("0"));
        return registrationDTO;
    }

    private RegistrationDTO masterInvalidProceduresOperation() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("TestName");
        registrationDTO.setClient(clientDTO);
        registrationDTO.setMaster(masterDTO);
        registrationDTO.setDate(Date.valueOf("2023-10-15"));
        registrationDTO.setProcedure(new BeautyProcedureDTO());
        registrationDTO.setPrice(new BigDecimal("15.55"));
        return registrationDTO;
    }
}