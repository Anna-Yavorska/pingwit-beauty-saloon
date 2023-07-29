package pingwit.beautysaloon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.converter.MasterConverter;
import pingwit.beautysaloon.exception.NotFoundException;
import pingwit.beautysaloon.repositiry.MasterRepository;
import pingwit.beautysaloon.repositiry.model.Master;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class MasterServiceImpl implements MasterService {
    private final MasterRepository masterRepository;
    private final MasterConverter masterConverter;

    public MasterServiceImpl(MasterRepository masterRepository, MasterConverter masterConverter) {
        this.masterRepository = masterRepository;
        this.masterConverter = masterConverter;
    }

    @Override
    public MasterDTO getMasterById(Integer id) {
        Master master = masterRepository.findById(id).orElseThrow(() -> new NotFoundException("Master not found: " + id));
        return masterConverter.convertMasterToDTO(master);
    }

    @Override
    public Collection<MasterDTO> getAllClients() {
        return masterConverter.convertMasterToDTO(masterRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createMaster(MasterDTO masterToCreate) {
        Master master = masterConverter.convertMasterToEntity(masterToCreate);
        Master savedMaster = masterRepository.save(master);
        return savedMaster.getId();
    }

    @Override
    @Transactional
    public MasterDTO updateMaster(Integer id, MasterDTO masterToUpdate) {
        Master master = masterRepository.findById(id).orElseThrow(() -> new NotFoundException("Master not found: " + id));
        Master entityToUpdate = masterConverter.convertMasterToEntity(masterToUpdate);
        entityToUpdate.setId(id);
        Master updatedMaster = masterRepository.save(entityToUpdate);
        return masterConverter.convertMasterToDTO(updatedMaster);
    }

    @Override
    @Transactional
    public void deleteMaster(Integer id) {
        Master master = masterRepository.findById(id).orElseThrow(() -> new NotFoundException("Master not found: " + id));
        masterRepository.delete(master);
    }
}
