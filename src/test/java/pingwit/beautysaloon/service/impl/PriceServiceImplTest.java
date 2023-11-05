package pingwit.beautysaloon.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;
import pingwit.beautysaloon.service.MasterService;
import pingwit.beautysaloon.service.PriceService;
import pingwit.beautysaloon.service.BeautyProcedureService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PriceServiceImplTest {
    private static final Integer ID = 1;
    private static final Integer NEW_PROCEDURE_ID = 2;

    private final MasterService masterService = mock(MasterService.class);
    private final BeautyProcedureService beautyProcedureService = mock(BeautyProcedureService.class);

    private final Collection<BeautyProcedureDTO> procedures = List.of(procedureDTO());

    private final PriceService target = new PriceServiceImpl(masterService, beautyProcedureService);

    @Test
    @DisplayName("Validation Error should not be thrown when master do procedure")
    void shouldNotThrow_whenCalculateIsPossible() {
        //given
        ReflectionTestUtils.setField(target, "baseRate", BigDecimal.valueOf(9.5));
        when(masterService.getMasterById(ID)).thenReturn(masterDTO());
        when(beautyProcedureService.getProcedureById(ID)).thenReturn(procedureDTO());

        //when
        assertDoesNotThrow(() -> target.calculatePrice(ID, ID));

        //then
        verify(masterService).getMasterById(ID);
        verify(beautyProcedureService).getProcedureById(ID);
    }

    @Test
    @DisplayName("Validation Error should be thrown when master does not do procedure")
    void shouldThrow_whenCalculateIsImpossible() {
        //given
        when(masterService.getMasterById(ID)).thenReturn(masterDTO());
        when(beautyProcedureService.getProcedureById(NEW_PROCEDURE_ID)).thenReturn(new BeautyProcedureDTO());

        String expected = String.format("master '%s' does not do procedure with id: %d", masterDTO().getName(), NEW_PROCEDURE_ID);

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.calculatePrice(ID, NEW_PROCEDURE_ID));

        //then
        assertThat(actual.getViolations()).containsOnly(expected);
    }

    private BeautyProcedureDTO procedureDTO() {
        BeautyProcedureDTO procedure = new BeautyProcedureDTO();
        procedure.setId(ID);
        procedure.setName("Women's haircut");
        procedure.setDescription("Haircut for medium length hair");
        procedure.setTime(new BigDecimal("1.25"));
        return procedure;
    }

    private MasterDTO masterDTO() {
        MasterDTO master = new MasterDTO();
        master.setId(ID);
        master.setName("Olia");
        master.setSurname("Kusenko");
        master.setPhone("98655667643");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        master.setProcedures(procedures);
        return master;
    }
}