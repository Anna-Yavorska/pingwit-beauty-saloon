package pingwit.beautysaloon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.converter.ProcedureConverter;
import pingwit.beautysaloon.exception.NotFoundException;
import pingwit.beautysaloon.repositiry.ProcedureRepository;
import pingwit.beautysaloon.repositiry.model.Procedure;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class ProcedureServiceImpl implements ProcedureService {
    private final ProcedureRepository procedureRepository;
    private final ProcedureConverter procedureConverter;

    public ProcedureServiceImpl(ProcedureRepository procedureRepository, ProcedureConverter procedureConverter) {
        this.procedureRepository = procedureRepository;
        this.procedureConverter = procedureConverter;
    }

    @Override
    public ProcedureDTO getProcedureById(Integer id) {
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new NotFoundException("Procedure not found: " + id));
        return procedureConverter.convertProcedureToDTO(procedure);
    }

    @Override
    public Collection<ProcedureDTO> getAllProcedures() {
        return procedureConverter.convertProcedureToDTO(procedureRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createProcedure(ProcedureDTO procedureToCreate) {
        Procedure procedure = procedureConverter.convertProcedureToEntity(procedureToCreate);
        Procedure savedProcedure = procedureRepository.save(procedure);
        return savedProcedure.getId();
    }

    @Override
    @Transactional
    public ProcedureDTO updateProcedure(Integer id, ProcedureDTO procedureToUpdate) {
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new NotFoundException("Procedure not found: " + id));
        Procedure entityToUpdate = procedureConverter.convertProcedureToEntity(procedureToUpdate);
        entityToUpdate.setId(id);
        procedureRepository.save(entityToUpdate);
        return procedureConverter.convertProcedureToDTO(entityToUpdate);
    }

    @Override
    @Transactional
    public void deleteProcedureById(Integer id) {
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new NotFoundException("Procedure not found: " + id));
        procedureRepository.delete(procedure);
    }
}
