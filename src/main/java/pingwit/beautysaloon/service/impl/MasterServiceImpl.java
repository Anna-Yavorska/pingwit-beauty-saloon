package pingwit.beautysaloon.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.converter.MasterConverter;
import pingwit.beautysaloon.exception.BeautySalonNotFoundException;
import pingwit.beautysaloon.repository.MasterRepository;
import pingwit.beautysaloon.repository.model.Master;
import pingwit.beautysaloon.service.MasterService;
import pingwit.beautysaloon.validator.MasterValidator;

import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MasterServiceImpl implements MasterService {
    private static final String EXCEPTION_MESSAGE = "Master not found: ";
    private final MasterRepository masterRepository;
    private final MasterConverter masterConverter;
    private final MasterValidator validator;

    public MasterServiceImpl(MasterRepository masterRepository, MasterConverter masterConverter, MasterValidator validator) {
        this.masterRepository = masterRepository;
        this.masterConverter = masterConverter;
        this.validator = validator;
    }

    @Override
    public MasterDTO getMasterById(Integer id) {
        Master master = masterRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        return masterConverter.convertMasterToDTO(master);
    }

    @Override
    public Collection<MasterDTO> getAllMasters() {
        return masterConverter.convertMasterToDTO(masterRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createMaster(MasterDTO masterToCreate) {
        validator.validateMaster(masterToCreate);
        Master master = masterConverter.convertMasterToEntity(masterToCreate);
        Master savedMaster = masterRepository.save(master);
        return savedMaster.getId();
    }

    @Override
    @Transactional
    public MasterDTO updateMaster(Integer id, MasterDTO masterToUpdate) {
        validator.validateMaster(masterToUpdate);
        if (masterRepository.findById(id).isEmpty()) {
            throw new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id);
        }
        Master entityToUpdate = masterConverter.convertMasterToEntity(masterToUpdate);
        entityToUpdate.setId(id);
        Master updatedMaster = masterRepository.save(entityToUpdate);
        return masterConverter.convertMasterToDTO(updatedMaster);
    }

    @Override
    @Transactional
    public void deleteMaster(Integer id) {
        Master master = masterRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        masterRepository.delete(master);
    }

    @Override
    public List<MasterDTO> getMastersByBeautyProcedure(String beautyProcedure) {
        Collection<MasterDTO> allMasters = getAllMasters();
        return allMasters.stream().filter(master -> master.getProcedures().stream().anyMatch(procedure -> procedure.getName().equalsIgnoreCase(beautyProcedure)))
                .toList();
    }
}
