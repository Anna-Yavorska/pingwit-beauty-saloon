package pingwit.beautysaloon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.repositiry.model.Procedure;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProcedureConverterTest {
    private static final Integer ID = 13;
    private static final String NAME = "TestName";
    private static final String DESCRIPTION = "Test description";
    private static final BigDecimal TIME = new BigDecimal(1);

    private final ProcedureConverter target = new ProcedureConverter();

    @Test
    @DisplayName("Should convert ProcedureDTO to Procedure")
    void shouldConvertProcedureDtoToEntity() {
        //given
        ProcedureDTO procedure = procedureDTO(ID);
        Procedure expected = entityProcedure(ID);

        //when
        Procedure actual = target.convertProcedureToEntity(procedure);

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getTime()).isEqualTo(expected.getTime());
    }

    @Test
    @DisplayName("Should convert Collection<ProcedureDTO> to List<Procedure>")
    void shouldConvertCollectionDtoToListEntity() {
        //given
        Collection<ProcedureDTO> procedures = List.of(procedureDTO(ID));
        List<Procedure> expected = List.of(entityProcedure(ID));

        //when
        List<Procedure> actual = target.convertProcedureToEntity(procedures);

        //then
        assertThat(actual).hasToString(expected.toString());
    }

    @Test
    @DisplayName("Should return Collection.EMPTY_LIST when List<ProductDTO> is null")
    void shouldReturnEmptyCollection_whenSourceIsNull() {
        //given
        Collection<ProcedureDTO> procedures = null;
        List<Procedure> expected = Collections.EMPTY_LIST;

        //when
        List<Procedure> actual = target.convertProcedureToEntity(procedures);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should convert Procedure to ProcedureDTO")
    void shouldConvertProcedureToDto() {
        //given
        Procedure procedure = entityProcedure(ID);
        ProcedureDTO expected = procedureDTO(ID);

        //when
        ProcedureDTO actual = target.convertProcedureToDTO(procedure);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should convert Collection<Procedure> to List<ProcedureDTO>")
    void shouldConvertCollectionEntityToListDto() {
        //given
        List<ProcedureDTO> expected = List.of(procedureDTO(ID));
        Collection<Procedure> procedures = List.of(entityProcedure(ID));

        //when
        List<ProcedureDTO> actual = target.convertProcedureToDTO(procedures);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }


    private Procedure entityProcedure(Integer id) {
        Procedure procedure = new Procedure();
        procedure.setId(id);
        procedure.setName(NAME);
        procedure.setDescription(DESCRIPTION);
        procedure.setTime(TIME);
        return procedure;
    }

    private ProcedureDTO procedureDTO(Integer id) {
        ProcedureDTO procedureDTO = new ProcedureDTO();
        procedureDTO.setId(id);
        procedureDTO.setName(NAME);
        procedureDTO.setDescription(DESCRIPTION);
        procedureDTO.setTime(TIME);
        return procedureDTO;
    }
}