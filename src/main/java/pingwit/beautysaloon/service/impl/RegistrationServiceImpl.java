package pingwit.beautysaloon.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.RegistrationDTO;
import pingwit.beautysaloon.converter.RegistrationConverter;
import pingwit.beautysaloon.exception.BeautySalonNotFoundException;
import pingwit.beautysaloon.repository.RegistrationRepository;
import pingwit.beautysaloon.repository.model.Registration;
import pingwit.beautysaloon.service.RegistrationService;
import pingwit.beautysaloon.validator.RegistrationValidator;


import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final String EXCEPTION_MESSAGE = "Operation not found: ";

    private final RegistrationRepository registrationRepository;
    private final RegistrationConverter registrationConverter;
    private final RegistrationValidator validator;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, RegistrationConverter registrationConverter, RegistrationValidator validator) {
        this.registrationRepository = registrationRepository;
        this.registrationConverter = registrationConverter;
        this.validator = validator;
    }

    @Override
    public RegistrationDTO getOperationById(Integer id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        return registrationConverter.convertOperationToDTO(registration);
    }

    @Override
    public List<RegistrationDTO> getAllOperations() {
        return registrationConverter.convertOperationToDTO(registrationRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createOperation(RegistrationDTO operationToCreate) {
        validator.validateOperation(operationToCreate);
        Registration registration = registrationConverter.convertOperationToEntity(operationToCreate);
        Registration savedRegistration = registrationRepository.save(registration);
        return savedRegistration.getId();
    }

    @Override
    public RegistrationDTO updateService(Integer id, RegistrationDTO operationToUpdate) {
        validator.validateOperation(operationToUpdate);
        if (registrationRepository.findById(id).isEmpty()) {
            throw new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id);
        }
        Registration entityToUpdate = registrationConverter.convertOperationToEntity(operationToUpdate);
        entityToUpdate.setId(id);
        Registration updatedRegistration = registrationRepository.save(entityToUpdate);
        return registrationConverter.convertOperationToDTO(updatedRegistration);
    }

    @Override
    @Transactional
    public void deleteOperation(Integer id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        registrationRepository.delete(registration);
    }
}
