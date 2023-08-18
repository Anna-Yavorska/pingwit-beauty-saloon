package pingwit.beautysaloon.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.controller.dto.ServiceDTO;
import pingwit.beautysaloon.exception.ValidationException;
import pingwit.beautysaloon.service.MasterService;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ServiceValidatorTest {
    private final MasterService masterService = mock(MasterService.class);
    private final ClientDTO clientDTO = mock(ClientDTO.class);
    private final MasterDTO masterDTO = mock(MasterDTO.class);
    private final ProcedureDTO procedureDTO = mock(ProcedureDTO.class);
    private final ServiceValidator target = new ServiceValidator(masterService);

    @Test
    @DisplayName("Validation Error should not be thrown when input service is valid")
    void shouldNotThrow_whenServiceIsValid() {
        //given
        ServiceDTO valid = validService();
        when(masterService.getMasterById(valid.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        assertDoesNotThrow(() -> target.validateService(valid));

        //then
        verify(masterService).getMasterById(valid.getMaster().getId());
        verify(masterDTO).getProcedures();
    }

    @Test
    @DisplayName("Validation Error should be thrown when name is blank")
    void shouldThrow_whenNameIsBlank() {
        //given
        ServiceDTO blankNameService = blackNameService();

        String expected = "name is blank";


        when(masterService.getMasterById(blankNameService.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(blankNameService));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when clientId is null")
    void shouldThrow_whenClientIsInvalid() {
        //given
        ServiceDTO clientInvalidService = clientInvalidService();
        String expected = "clientId can't be null";

        when(masterService.getMasterById(clientInvalidService.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(clientInvalidService));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when masterId is null")
    void shouldThrow_whenMasterIsInvalid() {
        //given
        ServiceDTO masterInvalidService = masterInvalidService();
        String expected = "masterId can't be null";

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(masterInvalidService));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);

    }

    @Test
    @DisplayName("Validation Error should be thrown when date is null")
    void shouldThrow_whenDateIsInvalid() {
        //given
        ServiceDTO invalidDateService = invalidDateService();
        String expected = "date can't be null";

        when(masterService.getMasterById(invalidDateService.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(invalidDateService));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when procedure is null")
    void shouldThrown_whenProcedureIsInvalid() {
        //given
        ServiceDTO invalidProcedureService = invalidProcedureService();
        String expected = "procedureId can't be null";

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(invalidProcedureService));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    @Test
    @DisplayName("Validation Error should be thrown when price is null")
    void shouldThrow_whenPriceIsNull() {
        //given
        ServiceDTO nullPriceService = nullPriceService();
        String expected = "price is null";

        when(masterService.getMasterById(nullPriceService.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(nullPriceService));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);

    }

    @Test
    @DisplayName("Validation Error should be thrown when price is invalid")
    void shouldThrow_whenPriceIsInvalid() {
        //given
        ServiceDTO invalidPriceService = invalidPriceService();
        String expected = "price must be greater than 0";

        when(masterService.getMasterById(invalidPriceService.getMaster().getId())).thenReturn(masterDTO);
        when(masterDTO.getProcedures()).thenReturn(List.of(procedureDTO));

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(invalidPriceService));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);

    }

    @Test
    @DisplayName("Validation Error should be thrown when master does not do procedure")
    void shouldThrow_whenMasterDoesNotDoProcedure() {
        //given
        ServiceDTO invalid = masterInvalidProceduresService();
        when(masterService.getMasterById(invalid.getMaster().getId())).thenReturn(masterDTO);

        String expected = String.format("'%s' master does not do procedure '%d'.", masterDTO.getName(), invalid.getProcedure().getId());

        //when
        ValidationException actual = assertThrows(ValidationException.class, () -> target.validateService(invalid));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);

    }

    private ServiceDTO validService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(procedureDTO);
        serviceDTO.setPrice(new BigDecimal("15.55"));
        return serviceDTO;
    }

    private ServiceDTO blackNameService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(" ");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(procedureDTO);
        serviceDTO.setPrice(new BigDecimal("15.55"));
        return serviceDTO;
    }

    private ServiceDTO clientInvalidService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(new ClientDTO());
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(procedureDTO);
        serviceDTO.setPrice(new BigDecimal("15.55"));
        return serviceDTO;
    }

    private ServiceDTO masterInvalidService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(new MasterDTO());
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(procedureDTO);
        serviceDTO.setPrice(new BigDecimal("15.55"));
        return serviceDTO;
    }

    private ServiceDTO invalidDateService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(null);
        serviceDTO.setProcedure(procedureDTO);
        serviceDTO.setPrice(new BigDecimal("15.55"));
        return serviceDTO;
    }

    private ServiceDTO invalidProcedureService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(null);
        serviceDTO.setPrice(new BigDecimal("15.55"));
        return serviceDTO;
    }

    private ServiceDTO nullPriceService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(procedureDTO);
        serviceDTO.setPrice(null);
        return serviceDTO;
    }

    private ServiceDTO invalidPriceService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(procedureDTO);
        serviceDTO.setPrice(new BigDecimal("0"));
        return serviceDTO;
    }

    private ServiceDTO masterInvalidProceduresService() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName("TestName");
        serviceDTO.setClient(clientDTO);
        serviceDTO.setMaster(masterDTO);
        serviceDTO.setDate(Date.valueOf("2023-10-15"));
        serviceDTO.setProcedure(new ProcedureDTO());
        serviceDTO.setPrice(new BigDecimal("15.55"));
        return serviceDTO;
    }
}