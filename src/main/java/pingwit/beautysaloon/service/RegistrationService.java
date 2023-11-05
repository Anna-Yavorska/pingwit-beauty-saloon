package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    RegistrationDTO getOperationById(Integer id);

    List<RegistrationDTO> getAllOperations();

    Integer createOperation(RegistrationDTO operationToCreate);

    RegistrationDTO updateService(Integer id, RegistrationDTO operationToUpdate);

    void deleteOperation(Integer id);
}
