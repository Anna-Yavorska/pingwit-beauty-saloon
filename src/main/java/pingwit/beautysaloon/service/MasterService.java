package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.MasterDTO;

import java.util.Collection;
import java.util.List;

public interface MasterService {
    MasterDTO getMasterById(Integer id);

    Collection<MasterDTO> getAllMasters();

    Integer createMaster(MasterDTO masterToCreate);

    MasterDTO updateMaster(Integer id, MasterDTO masterToUpdate);

    void deleteMaster(Integer id);
    List<MasterDTO> getMastersByBeautyProcedure(String beautyProcedure);
}
