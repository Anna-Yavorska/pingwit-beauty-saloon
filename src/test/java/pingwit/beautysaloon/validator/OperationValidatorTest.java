package pingwit.beautysaloon.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.controller.dto.OperationDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;
import pingwit.beautysaloon.service.MasterService;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OperationValidatorTest {
    private final MasterService masterService = mock(MasterService.class);
    private final ClientDTO clientDTO = mock(ClientDTO.class);
    private final MasterDTO masterDTO = mock(MasterDTO.class);
    private final ProcedureDTO procedureDTO = mock(ProcedureDTO.class);
    private final OperationValidator target = new OperationValidator(masterService);

    @Test
    @DisplayName("Validation Error should not be thrown when input operation is valid")
    void shouldNotThrow_whenOperationIsValid() {
        //given
        OperationDTO valid = validOperation();
        when(masterService.getMasterById(valid.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

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
        OperationDTO blankNameOperation = blackNameOperation();

        String expected = "name is blank";

        when(masterService.getMasterById(blankNameOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(blankNameOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when clientId is null")
    void shouldThrow_whenClientIsInvalid() {
        //given
        OperationDTO clientInvalidOperation = clientInvalidOperation();
        String expected = "clientId can't be null";

        when(masterService.getMasterById(clientInvalidOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(clientInvalidOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when masterId is null")
    void shouldThrow_whenMasterIsInvalid() {
        //given
        OperationDTO masterInvalidOperation = masterInvalidOperation();
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
        OperationDTO invalidDateOperation = invalidDateOperation();
        String expected = "date can't be null";

        when(masterService.getMasterById(invalidDateOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(invalidDateOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when procedure is null")
    void shouldThrown_whenProcedureIsInvalid() {
        //given
        OperationDTO invalidProcedureOperation = invalidProcedureOperation();
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
        OperationDTO nullPriceOperation = nullPriceOperation();
        String expected = "price is null";

        when(masterService.getMasterById(nullPriceOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(nullPriceOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when price is invalid")
    void shouldThrow_whenPriceIsInvalid() {
        //given
        OperationDTO invalidPriceOperation = invalidPriceOperation();
        String expected = "price must be greater than 0";

        when(masterService.getMasterById(invalidPriceOperation.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(invalidPriceOperation));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when master does not do procedure")
    void shouldThrow_whenMasterDoesNotDoProcedure() {
        //given
        OperationDTO invalid = masterInvalidProceduresOperation();
        when(masterService.getMasterById(invalid.getMaster().getId())).thenReturn(masterDTO);

        String expected = String.format("'%s' master does not do procedure '%d'.", masterDTO.getName(), invalid.getProcedure().getId());

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateOperation(invalid));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    private OperationDTO validOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(procedureDTO);
        operationDTO.setPrice(new BigDecimal("15.55"));
        return operationDTO;
    }

    private OperationDTO blackNameOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName(" ");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(procedureDTO);
        operationDTO.setPrice(new BigDecimal("15.55"));
        return operationDTO;
    }

    private OperationDTO clientInvalidOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(new ClientDTO());
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(procedureDTO);
        operationDTO.setPrice(new BigDecimal("15.55"));
        return operationDTO;
    }

    private OperationDTO masterInvalidOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(new MasterDTO());
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(procedureDTO);
        operationDTO.setPrice(new BigDecimal("15.55"));
        return operationDTO;
    }

    private OperationDTO invalidDateOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(null);
        operationDTO.setProcedure(procedureDTO);
        operationDTO.setPrice(new BigDecimal("15.55"));
        return operationDTO;
    }

    private OperationDTO invalidProcedureOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(null);
        operationDTO.setPrice(new BigDecimal("15.55"));
        return operationDTO;
    }

    private OperationDTO nullPriceOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(procedureDTO);
        operationDTO.setPrice(null);
        return operationDTO;
    }

    private OperationDTO invalidPriceOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(procedureDTO);
        operationDTO.setPrice(new BigDecimal("0"));
        return operationDTO;
    }

    private OperationDTO masterInvalidProceduresOperation() {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setName("TestName");
        operationDTO.setClient(clientDTO);
        operationDTO.setMaster(masterDTO);
        operationDTO.setDate(Date.valueOf("2023-10-15"));
        operationDTO.setProcedure(new ProcedureDTO());
        operationDTO.setPrice(new BigDecimal("15.55"));
        return operationDTO;
    }
}