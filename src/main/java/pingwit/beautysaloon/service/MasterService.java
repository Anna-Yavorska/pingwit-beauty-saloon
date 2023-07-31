package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.MasterDTO;

import java.util.Collection;

public interface MasterService {
    MasterDTO getMasterById(Integer id);

    Collection<MasterDTO> getAllClients();

    Integer createMaster(MasterDTO masterToCreate);

    MasterDTO updateMaster(Integer id, MasterDTO masterToUpdate);

    void deleteMaster(Integer id);
}
