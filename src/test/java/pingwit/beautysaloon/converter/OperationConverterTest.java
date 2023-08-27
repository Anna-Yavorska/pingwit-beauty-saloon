package pingwit.beautysaloon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.OperationDTO;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.repositiry.model.Client;
import pingwit.beautysaloon.repositiry.model.Master;
import pingwit.beautysaloon.repositiry.model.Operation;
import pingwit.beautysaloon.repositiry.model.Procedure;

import java.math.BigDecimal;
import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OperationConverterTest {
    private static final Integer ID = 789;
    private final ClientDTO clientDTO = new ClientDTO();
    private final MasterDTO masterDTO = new MasterDTO();
    private final ProcedureDTO procedureDTO = new ProcedureDTO();

    private final Client client = new Client();
    private final Master master = new Master();
    private final Procedure procedure = new Procedure();

    private final ClientConverter clientConverter = mock(ClientConverter.class);
    private final MasterConverter masterConverter = mock(MasterConverter.class);
    private final ProcedureConverter procedureConverter = mock(ProcedureConverter.class);

    private final OperationConverter target = new OperationConverter(clientConverter, masterConverter, procedureConverter);

    @Test
    @DisplayName("Should convert OperationDTO to Operation")
    void shouldConvertOperationDtoToEntity(){
        //given
        OperationDTO operation = operationDTO(ID);
        Operation expected = entityOperation(ID);

        when(clientConverter.convertClientToEntity(operation.getClient())).thenReturn(client);
        when(masterConverter.convertMasterToEntity(operation.getMaster())).thenReturn(master);
        when(procedureConverter.convertProcedureToEntity(operation.getProcedure())).thenReturn(procedure);

        //when
        Operation actual = target.convertOperationToEntity(operation);

        //then
        assertThat(actual).hasToString(expected.toString());
        verify(clientConverter).convertClientToEntity(operation.getClient());
        verify(masterConverter).convertMasterToEntity(operation.getMaster());
        verify(procedureConverter).convertProcedureToEntity(operation.getProcedure());
    }
    @Test
    @DisplayName("Should convert Operation to OperationDTO")
    void shouldConvertOperationToDto(){
        //given
        Operation operation = entityOperation(ID);
        OperationDTO expected = operationDTO(ID);

        when(clientConverter.convertClientToDTO(operation.getClient())).thenReturn(clientDTO);
        when(masterConverter.convertMasterToDTO(operation.getMaster())).thenReturn(masterDTO);
        when(procedureConverter.convertProcedureToDTO(operation.getProcedure())).thenReturn(procedureDTO);

        //when
        OperationDTO actual = target.convertOperationToDTO(operation);

        //then
        assertThat(actual).hasToString(expected.toString());
        verify(clientConverter).convertClientToDTO(operation.getClient());
        verify(masterConverter).convertMasterToDTO(operation.getMaster());
        verify(procedureConverter).convertProcedureToDTO(operation.getProcedure());
    }

    private OperationDTO operationDTO(Integer id) {
        OperationDTO operation = new OperationDTO();
        operation.setId(id);
        operation.setName("TestName");
        operation.setClient(clientDTO);
        operation.setMaster(masterDTO);
        operation.setDate(Date.valueOf("2023-11-20"));
        operation.setProcedure(procedureDTO);
        operation.setPrice(new BigDecimal("25.8"));
        return operation;
    }
    private Operation entityOperation(Integer id) {
        Operation operation = new Operation();
        operation.setId(id);
        operation.setName("TestName");
        operation.setClient(client);
        operation.setMaster(master);
        operation.setDate(Date.valueOf("2023-11-20"));
        operation.setProcedure(procedure);
        operation.setPrice(new BigDecimal("25.8"));
        return operation;
    }
}