package pingwit.beautysaloon.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.converter.BeautyProcedureConverter;
import pingwit.beautysaloon.exception.BeautySalonNotFoundException;
import pingwit.beautysaloon.repository.BeautyProcedureRepository;
import pingwit.beautysaloon.repository.model.BeautyProcedure;
import pingwit.beautysaloon.service.BeautyProcedureService;
import pingwit.beautysaloon.validator.BeautyProcedureValidator;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class BeautyBeautyProcedureServiceImpl implements BeautyProcedureService {
    private static final String EXCEPTION_MESSAGE = "Procedure not found: ";

    private final BeautyProcedureRepository beautyProcedureRepository;
    private final BeautyProcedureConverter beautyProcedureConverter;
    private final BeautyProcedureValidator validator;

    public BeautyBeautyProcedureServiceImpl(BeautyProcedureRepository beautyProcedureRepository, BeautyProcedureConverter beautyProcedureConverter, BeautyProcedureValidator validator) {
        this.beautyProcedureRepository = beautyProcedureRepository;
        this.beautyProcedureConverter = beautyProcedureConverter;
        this.validator = validator;
    }

    @Override
    public BeautyProcedureDTO getProcedureById(Integer id) {
        BeautyProcedure beautyProcedure = beautyProcedureRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        return beautyProcedureConverter.convertProcedureToDTO(beautyProcedure);
    }

    @Override
    public Collection<BeautyProcedureDTO> getAllProcedures() {
        return beautyProcedureConverter.convertProcedureToDTO(beautyProcedureRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createProcedure(BeautyProcedureDTO procedureToCreate) {
        validator.validateProcedure(procedureToCreate);
        BeautyProcedure beautyProcedure = beautyProcedureConverter.convertProcedureToEntity(procedureToCreate);
        BeautyProcedure savedBeautyProcedure = beautyProcedureRepository.save(beautyProcedure);
        return savedBeautyProcedure.getId();
    }

    @Override
    @Transactional
    public BeautyProcedureDTO updateProcedure(Integer id, BeautyProcedureDTO procedureToUpdate) {
        validator.validateProcedure(procedureToUpdate);
        if (beautyProcedureRepository.findById(id).isEmpty()) {
            throw new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id);
        }
        BeautyProcedure entityToUpdate = beautyProcedureConverter.convertProcedureToEntity(procedureToUpdate);
        entityToUpdate.setId(id);
        beautyProcedureRepository.save(entityToUpdate);
        return beautyProcedureConverter.convertProcedureToDTO(entityToUpdate);
    }

    @Override
    @Transactional
    public void deleteProcedureById(Integer id) {
        BeautyProcedure beautyProcedure = beautyProcedureRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        beautyProcedureRepository.delete(beautyProcedure);
    }
}
