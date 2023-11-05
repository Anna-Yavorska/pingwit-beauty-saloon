package pingwit.beautysaloon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.repository.model.BeautyProcedure;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeautyProcedureConverterTest {
    private static final Integer ID = 13;
    private static final String NAME = "TestName";
    private static final String DESCRIPTION = "Test description";
    private static final BigDecimal TIME = new BigDecimal(1);

    private final BeautyProcedureConverter target = new BeautyProcedureConverter();

    @Test
    @DisplayName("Should convert ProcedureDTO to Procedure")
    void shouldConvertProcedureDtoToEntity() {
        //given
        BeautyProcedureDTO procedure = procedureDTO(ID);
        BeautyProcedure expected = entityProcedure(ID);

        //when
        BeautyProcedure actual = target.convertProcedureToEntity(procedure);

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
        Collection<BeautyProcedureDTO> procedures = List.of(procedureDTO(ID));
        List<BeautyProcedure> expected = List.of(entityProcedure(ID));

        //when
        List<BeautyProcedure> actual = target.convertProcedureToEntity(procedures);

        //then
        assertThat(actual).hasToString(expected.toString());
    }

    @Test
    @DisplayName("Should return Collection.EMPTY_LIST when List<ProductDTO> is null")
    void shouldReturnEmptyCollection_whenSourceIsNull() {
        //given
        Collection<BeautyProcedureDTO> procedures = null;
        List<BeautyProcedure> expected = Collections.EMPTY_LIST;

        //when
        List<BeautyProcedure> actual = target.convertProcedureToEntity(procedures);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should convert Procedure to ProcedureDTO")
    void shouldConvertProcedureToDto() {
        //given
        BeautyProcedure beautyProcedure = entityProcedure(ID);
        BeautyProcedureDTO expected = procedureDTO(ID);

        //when
        BeautyProcedureDTO actual = target.convertProcedureToDTO(beautyProcedure);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should convert Collection<Procedure> to List<ProcedureDTO>")
    void shouldConvertCollectionEntityToListDto() {
        //given
        List<BeautyProcedureDTO> expected = List.of(procedureDTO(ID));
        Collection<BeautyProcedure> beautyProcedures = List.of(entityProcedure(ID));

        //when
        List<BeautyProcedureDTO> actual = target.convertProcedureToDTO(beautyProcedures);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }


    private BeautyProcedure entityProcedure(Integer id) {
        BeautyProcedure beautyProcedure = new BeautyProcedure();
        beautyProcedure.setId(id);
        beautyProcedure.setName(NAME);
        beautyProcedure.setDescription(DESCRIPTION);
        beautyProcedure.setTime(TIME);
        return beautyProcedure;
    }

    private BeautyProcedureDTO procedureDTO(Integer id) {
        BeautyProcedureDTO beautyProcedureDTO = new BeautyProcedureDTO();
        beautyProcedureDTO.setId(id);
        beautyProcedureDTO.setName(NAME);
        beautyProcedureDTO.setDescription(DESCRIPTION);
        beautyProcedureDTO.setTime(TIME);
        return beautyProcedureDTO;
    }
}