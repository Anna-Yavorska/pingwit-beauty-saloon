package pingwit.beautysaloon.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.converter.ProcedureConverter;
import pingwit.beautysaloon.exception.NotFoundException;
import pingwit.beautysaloon.repositiry.ProcedureRepository;
import pingwit.beautysaloon.repositiry.model.Procedure;
import pingwit.beautysaloon.service.ProcedureService;
import pingwit.beautysaloon.validator.ProcedureValidator;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class ProcedureServiceImpl implements ProcedureService {
    private static final String EXCEPTION_MESSAGE = "Procedure not found: ";

    private final ProcedureRepository procedureRepository;
    private final ProcedureConverter procedureConverter;
    private final ProcedureValidator validator;

    public ProcedureServiceImpl(ProcedureRepository procedureRepository, ProcedureConverter procedureConverter, ProcedureValidator validator) {
        this.procedureRepository = procedureRepository;
        this.procedureConverter = procedureConverter;
        this.validator = validator;
    }

    @Override
    public ProcedureDTO getProcedureById(Integer id) {
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new NotFoundException(EXCEPTION_MESSAGE + id));
        return procedureConverter.convertProcedureToDTO(procedure);
    }

    @Override
    public Collection<ProcedureDTO> getAllProcedures() {
        return procedureConverter.convertProcedureToDTO(procedureRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createProcedure(ProcedureDTO procedureToCreate) {
        validator.validateProcedure(procedureToCreate);
        Procedure procedure = procedureConverter.convertProcedureToEntity(procedureToCreate);
        Procedure savedProcedure = procedureRepository.save(procedure);
        return savedProcedure.getId();
    }

    @Override
    @Transactional
    public ProcedureDTO updateProcedure(Integer id, ProcedureDTO procedureToUpdate) {
        validator.validateProcedure(procedureToUpdate);
        if (procedureRepository.findById(id).isEmpty()) {
            throw new NotFoundException(EXCEPTION_MESSAGE + id);
        }
        Procedure entityToUpdate = procedureConverter.convertProcedureToEntity(procedureToUpdate);
        entityToUpdate.setId(id);
        procedureRepository.save(entityToUpdate);
        return procedureConverter.convertProcedureToDTO(entityToUpdate);
    }

    @Override
    @Transactional
    public void deleteProcedureById(Integer id) {
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new NotFoundException(EXCEPTION_MESSAGE + id));
        procedureRepository.delete(procedure);
    }
}
