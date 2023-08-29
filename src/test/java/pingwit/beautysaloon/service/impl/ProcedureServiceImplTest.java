package pingwit.beautysaloon.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.converter.ProcedureConverter;
import pingwit.beautysaloon.repositiry.ProcedureRepository;
import pingwit.beautysaloon.repositiry.model.Procedure;
import pingwit.beautysaloon.service.ProcedureService;
import pingwit.beautysaloon.validator.ProcedureValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProcedureServiceImplTest {
    private final ProcedureRepository procedureRepository = mock(ProcedureRepository.class);
    private final ProcedureConverter procedureConverter = mock(ProcedureConverter.class);
    private final ProcedureValidator validator = mock(ProcedureValidator.class);

    private final ProcedureService target = new ProcedureServiceImpl(procedureRepository, procedureConverter, validator);
    private final Integer procedureId = 1;

    @Test
    @DisplayName("Should find procedure by Id")
    void shouldFindProcedureById() {
        //given
        Procedure foundProcedure = new Procedure();
        when(procedureRepository.findById(procedureId)).thenReturn(Optional.of(foundProcedure));

        //when
        ProcedureDTO procedureById = target.getProcedureById(procedureId);

        //then
        verify(procedureRepository).findById(procedureId);
        verify(procedureConverter).convertProcedureToDTO(foundProcedure);
    }

    @Test
    @DisplayName("Should find all procedures")
    void shouldFindAllProcedures() {
        //given
        List<Procedure> procedures = List.of(new Procedure());
        when(procedureRepository.findAll()).thenReturn(procedures);

        //when
        target.getAllProcedures();

        //then
        verify(procedureRepository).findAll();
        verify(procedureConverter).convertProcedureToDTO(procedures);
    }

    @Test
    @DisplayName("Should create procedure")
    void createProcedure() {
        //given
        ProcedureDTO procedureDTO = new ProcedureDTO();
        Procedure procedureToSave = new Procedure();
        Procedure savedProcedure = savedProcedure();

        when(procedureConverter.convertProcedureToEntity(procedureDTO)).thenReturn(procedureToSave);
        when(procedureRepository.save(procedureToSave)).thenReturn(savedProcedure);
        //when
        Integer actualId = target.createProcedure(procedureDTO);

        //then
        verify(validator).validateProcedure(procedureDTO);
        verify(procedureConverter).convertProcedureToEntity(procedureDTO);
        verify(procedureRepository).save(procedureToSave);

        assertThat(actualId).isEqualTo(savedProcedure.getId());
    }

    @Test
    @DisplayName("Should update procedure by Id")
    void updateProcedure() {
        //given
        ProcedureDTO procedureDTO = new ProcedureDTO();
        Procedure existingProcedure = savedProcedure();

        when(procedureRepository.findById(procedureId)).thenReturn(Optional.of(existingProcedure));
        when(procedureConverter.convertProcedureToEntity(procedureDTO)).thenReturn(existingProcedure);
        when(procedureRepository.save(existingProcedure)).thenReturn(existingProcedure);

        //when
        ProcedureDTO actualProcedure = target.updateProcedure(procedureId, procedureDTO);

        //then
        verify(validator).validateProcedure(procedureDTO);
        verify(procedureRepository).findById(procedureId);
        verify(procedureConverter).convertProcedureToEntity(procedureDTO);
        verify(procedureRepository).save(existingProcedure);
        ProcedureDTO procedure = verify(procedureConverter).convertProcedureToDTO(existingProcedure);

        assertThat(actualProcedure).isEqualTo(procedure);
    }


    @Test
    @DisplayName("Should delete procedure by Id")
    void shouldDeleteProcedure() {
        //given
        Procedure procedureToDelete = savedProcedure();

        when(procedureRepository.findById(procedureId)).thenReturn(Optional.of(procedureToDelete));

        //when
        target.deleteProcedureById(procedureId);

        //then
        verify(procedureRepository).findById(procedureId);
        verify(procedureRepository).delete(procedureToDelete);
    }

    private Procedure savedProcedure() {
        Procedure procedure = new Procedure();
        procedure.setId(procedureId);
        procedure.setName("TestName");
        procedure.setDescription("TestDescription");
        procedure.setTime(new BigDecimal("1"));
        return procedure;
    }
}