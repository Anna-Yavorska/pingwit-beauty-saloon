package pingwit.beautysaloon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.repositiry.model.Master;
import pingwit.beautysaloon.repositiry.model.Procedure;
import pingwit.beautysaloon.repositiry.model.ProfLevel;
import pingwit.beautysaloon.repositiry.model.Profession;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MasterConverterTest {
    private static final Integer ID = 13;
    private static final String NAME = "TestName";
    private static final String SURNAME = "TestSurname";
    private static final String PHONE = "911";
    private static final String PROF_LEVEL = "senior";
    private static final String PROFESSION = "hairdresser";
    private static final Collection<ProcedureDTO> PROCEDURES_DTO = List.of(new ProcedureDTO());
    private static final List<Procedure> PROCEDURES_ENTITY = List.of(new Procedure());

    private final ProcedureConverter procedureConverter = mock(ProcedureConverter.class);
    private final MasterConverter target = new MasterConverter(procedureConverter);

    @Test
    @DisplayName("Should convert MasterDTO to Master")
    void shouldConvertMasterDtoToEntity() {
        //given
        MasterDTO master = masterDTO(ID);
        Master expected = entityMaster(ID);

        when(procedureConverter.convertProcedureToEntity(master.getProcedures())).thenReturn(PROCEDURES_ENTITY);

        //when
        Master actual = target.convertMasterToEntity(master);

        //then
        assertThat(actual).hasToString(expected.toString());
        verify(procedureConverter).convertProcedureToEntity(master.getProcedures());

    }

    @Test
    @DisplayName("Should convert Master to MasterDTO")
    void shouldConvertMasterToDto() {
        //given
        Master master = entityMaster(ID);
        MasterDTO expected = masterDTO(ID);

        when(procedureConverter.convertProcedureToDTO(master.getProcedures())).thenReturn(PROCEDURES_DTO.stream().toList());

        //when
        MasterDTO actual = target.convertMasterToDTO(master);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(procedureConverter).convertProcedureToDTO(master.getProcedures());

    }

    @Test
    @DisplayName("Should convert Collection<Master> to List<MasterDTO>")
    void shouldConvertCollectionEntityToListDto() {
        //given
        Collection<Master> masters = List.of(entityMaster(ID));
        List<MasterDTO> expected = List.of(masterDTO(ID));
        List<Procedure> list = masters.stream().flatMap(master -> master.getProcedures().stream()).toList();

        when(procedureConverter.convertProcedureToDTO(list)).thenReturn(PROCEDURES_DTO.stream().toList());

        //when
        List<MasterDTO> actual = target.convertMasterToDTO(masters);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        verify(procedureConverter).convertProcedureToDTO(list);
    }

    private Master entityMaster(Integer id) {
        Master master = new Master();
        master.setId(id);
        master.setName(NAME);
        master.setSurname(SURNAME);
        master.setPhone(PHONE);
        master.setProfLevel(ProfLevel.findByValue(PROF_LEVEL));
        master.setProfession(Profession.findByValue(PROFESSION));
        master.setProcedures(PROCEDURES_ENTITY);
        return master;
    }

    private MasterDTO masterDTO(Integer id) {
        MasterDTO masterDTO = new MasterDTO();
        masterDTO.setId(id);
        masterDTO.setName(NAME);
        masterDTO.setSurname(SURNAME);
        masterDTO.setPhone(PHONE);
        masterDTO.setProfLevel(PROF_LEVEL);
        masterDTO.setProfession(PROFESSION);
        masterDTO.setProcedures(PROCEDURES_DTO);
        return masterDTO;
    }
}