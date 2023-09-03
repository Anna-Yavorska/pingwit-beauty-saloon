package pingwit.beautysaloon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.RegistrationDTO;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.repository.model.Client;
import pingwit.beautysaloon.repository.model.Master;
import pingwit.beautysaloon.repository.model.Registration;
import pingwit.beautysaloon.repository.model.BeautyProcedure;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegistrationConverterTest {
    private static final Integer ID = 789;
    private final ClientDTO clientDTO = new ClientDTO();
    private final MasterDTO masterDTO = new MasterDTO();
    private final BeautyProcedureDTO beautyProcedureDTO = new BeautyProcedureDTO();

    private final Client client = new Client();
    private final Master master = new Master();
    private final BeautyProcedure beautyProcedure = new BeautyProcedure();

    private final ClientConverter clientConverter = mock(ClientConverter.class);
    private final MasterConverter masterConverter = mock(MasterConverter.class);
    private final BeautyProcedureConverter beautyProcedureConverter = mock(BeautyProcedureConverter.class);

    private final RegistrationConverter target = new RegistrationConverter(clientConverter, masterConverter, beautyProcedureConverter);

    @Test
    @DisplayName("Should convert OperationDTO to Operation")
    void shouldConvertOperationDtoToEntity() {
        //given
        RegistrationDTO operation = operationDTO(ID);
        Registration expected = entityOperation(ID);

        when(clientConverter.convertClientToEntity(operation.getClient())).thenReturn(client);
        when(masterConverter.convertMasterToEntity(operation.getMaster())).thenReturn(master);
        when(beautyProcedureConverter.convertProcedureToEntity(operation.getProcedure())).thenReturn(beautyProcedure);

        //when
        Registration actual = target.convertOperationToEntity(operation);

        //then
        assertThat(actual).hasToString(expected.toString());
        verify(clientConverter).convertClientToEntity(operation.getClient());
        verify(masterConverter).convertMasterToEntity(operation.getMaster());
        verify(beautyProcedureConverter).convertProcedureToEntity(operation.getProcedure());
    }

    @Test
    @DisplayName("Should convert Operation to OperationDTO")
    void shouldConvertOperationToDto() {
        //given
        Registration registration = entityOperation(ID);
        RegistrationDTO expected = operationDTO(ID);

        when(clientConverter.convertClientToDTO(registration.getClient())).thenReturn(clientDTO);
        when(masterConverter.convertMasterToDTO(registration.getMaster())).thenReturn(masterDTO);
        when(beautyProcedureConverter.convertProcedureToDTO(registration.getProcedure())).thenReturn(beautyProcedureDTO);

        //when
        RegistrationDTO actual = target.convertOperationToDTO(registration);

        //then
        assertThat(actual).hasToString(expected.toString());
        verify(clientConverter).convertClientToDTO(registration.getClient());
        verify(masterConverter).convertMasterToDTO(registration.getMaster());
        verify(beautyProcedureConverter).convertProcedureToDTO(registration.getProcedure());
    }

    @Test
    @DisplayName("Should convert Collection<Operation> to List<OperationDTO>")
    void shouldConvertCollectionEntityToListDto() {
        //given
        Collection<Registration> registrations = Arrays.asList(entityOperation(ID), entityOperation(ID + 1));
        List<RegistrationDTO> expected = Arrays.asList(operationDTO(ID), operationDTO(ID + 1));

        when(clientConverter.convertClientToDTO(client)).thenReturn(clientDTO);
        when(masterConverter.convertMasterToDTO(master)).thenReturn(masterDTO);
        when(beautyProcedureConverter.convertProcedureToDTO(beautyProcedure)).thenReturn(beautyProcedureDTO);

        //when
        List<RegistrationDTO> actual = target.convertOperationToDTO(registrations);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        verify(clientConverter, times(2)).convertClientToDTO(client);
        verify(masterConverter, times(2)).convertMasterToDTO(master);
        verify(beautyProcedureConverter, times(2)).convertProcedureToDTO(beautyProcedure);
    }

    private RegistrationDTO operationDTO(Integer id) {
        RegistrationDTO operation = new RegistrationDTO();
        operation.setId(id);
        operation.setName("TestName");
        operation.setClient(clientDTO);
        operation.setMaster(masterDTO);
        operation.setDate(Date.valueOf("2023-11-20"));
        operation.setProcedure(beautyProcedureDTO);
        operation.setPrice(new BigDecimal("25.8"));
        return operation;
    }

    private Registration entityOperation(Integer id) {
        Registration registration = new Registration();
        registration.setId(id);
        registration.setName("TestName");
        registration.setClient(client);
        registration.setMaster(master);
        registration.setDate(Date.valueOf("2023-11-20"));
        registration.setProcedure(beautyProcedure);
        registration.setPrice(new BigDecimal("25.8"));
        return registration;
    }
}