package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.OperationDTO;

import java.util.List;

public interface OperationService {
    OperationDTO getOperationById(Integer id);

    List<OperationDTO> getAllOperations();

    Integer createOperation(OperationDTO operationToCreate);

    OperationDTO updateService(Integer id, OperationDTO operationToUpdate);

    void deleteOperation(Integer id);
}
