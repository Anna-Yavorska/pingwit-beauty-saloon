package pingwit.beautysaloon.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.converter.BeautyProcedureConverter;
import pingwit.beautysaloon.repository.BeautyProcedureRepository;
import pingwit.beautysaloon.repository.model.BeautyProcedure;
import pingwit.beautysaloon.service.BeautyProcedureService;
import pingwit.beautysaloon.validator.BeautyProcedureValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BeautyProcedureServiceImplTest {
    private final BeautyProcedureRepository beautyProcedureRepository = mock(BeautyProcedureRepository.class);
    private final BeautyProcedureConverter beautyProcedureConverter = mock(BeautyProcedureConverter.class);
    private final BeautyProcedureValidator validator = mock(BeautyProcedureValidator.class);

    private final BeautyProcedureService target = new BeautyBeautyProcedureServiceImpl(beautyProcedureRepository, beautyProcedureConverter, validator);
    private final Integer procedureId = 1;

    @Test
    @DisplayName("Should find procedure by Id")
    void shouldFindProcedureById() {
        //given
        BeautyProcedure foundBeautyProcedure = new BeautyProcedure();
        when(beautyProcedureRepository.findById(procedureId)).thenReturn(Optional.of(foundBeautyProcedure));

        //when
        BeautyProcedureDTO procedureById = target.getProcedureById(procedureId);

        //then
        verify(beautyProcedureRepository).findById(procedureId);
        verify(beautyProcedureConverter).convertProcedureToDTO(foundBeautyProcedure);
    }

    @Test
    @DisplayName("Should find all procedures")
    void shouldFindAllProcedures() {
        //given
        List<BeautyProcedure> beautyProcedures = List.of(new BeautyProcedure());
        when(beautyProcedureRepository.findAll()).thenReturn(beautyProcedures);

        //when
        target.getAllProcedures();

        //then
        verify(beautyProcedureRepository).findAll();
        verify(beautyProcedureConverter).convertProcedureToDTO(beautyProcedures);
    }

    @Test
    @DisplayName("Should create procedure")
    void createProcedure() {
        //given
        BeautyProcedureDTO beautyProcedureDTO = new BeautyProcedureDTO();
        BeautyProcedure beautyProcedureToSave = new BeautyProcedure();
        BeautyProcedure savedBeautyProcedure = savedProcedure();

        when(beautyProcedureConverter.convertProcedureToEntity(beautyProcedureDTO)).thenReturn(beautyProcedureToSave);
        when(beautyProcedureRepository.save(beautyProcedureToSave)).thenReturn(savedBeautyProcedure);
        //when
        Integer actualId = target.createProcedure(beautyProcedureDTO);

        //then
        verify(validator).validateProcedure(beautyProcedureDTO);
        verify(beautyProcedureConverter).convertProcedureToEntity(beautyProcedureDTO);
        verify(beautyProcedureRepository).save(beautyProcedureToSave);

        assertThat(actualId).isEqualTo(savedBeautyProcedure.getId());
    }

    @Test
    @DisplayName("Should update procedure by Id")
    void updateProcedure() {
        //given
        BeautyProcedureDTO beautyProcedureDTO = new BeautyProcedureDTO();
        BeautyProcedure existingBeautyProcedure = savedProcedure();

        when(beautyProcedureRepository.findById(procedureId)).thenReturn(Optional.of(existingBeautyProcedure));
        when(beautyProcedureConverter.convertProcedureToEntity(beautyProcedureDTO)).thenReturn(existingBeautyProcedure);
        when(beautyProcedureRepository.save(existingBeautyProcedure)).thenReturn(existingBeautyProcedure);

        //when
        BeautyProcedureDTO actualProcedure = target.updateProcedure(procedureId, beautyProcedureDTO);

        //then
        verify(validator).validateProcedure(beautyProcedureDTO);
        verify(beautyProcedureRepository).findById(procedureId);
        verify(beautyProcedureConverter).convertProcedureToEntity(beautyProcedureDTO);
        verify(beautyProcedureRepository).save(existingBeautyProcedure);
        BeautyProcedureDTO procedure = verify(beautyProcedureConverter).convertProcedureToDTO(existingBeautyProcedure);

        assertThat(actualProcedure).isEqualTo(procedure);
    }


    @Test
    @DisplayName("Should delete procedure by Id")
    void shouldDeleteProcedure() {
        //given
        BeautyProcedure beautyProcedureToDelete = savedProcedure();

        when(beautyProcedureRepository.findById(procedureId)).thenReturn(Optional.of(beautyProcedureToDelete));

        //when
        target.deleteProcedureById(procedureId);

        //then
        verify(beautyProcedureRepository).findById(procedureId);
        verify(beautyProcedureRepository).delete(beautyProcedureToDelete);
    }

    private BeautyProcedure savedProcedure() {
        BeautyProcedure beautyProcedure = new BeautyProcedure();
        beautyProcedure.setId(procedureId);
        beautyProcedure.setName("TestName");
        beautyProcedure.setDescription("TestDescription");
        beautyProcedure.setTime(new BigDecimal("1"));
        return beautyProcedure;
    }
}