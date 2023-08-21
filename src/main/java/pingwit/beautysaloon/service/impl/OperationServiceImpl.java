package pingwit.beautysaloon.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.OperationDTO;
import pingwit.beautysaloon.converter.OperationConverter;
import pingwit.beautysaloon.exception.NotFoundException;
import pingwit.beautysaloon.repositiry.OperationRepository;
import pingwit.beautysaloon.repositiry.model.Operation;
import pingwit.beautysaloon.service.OperationService;
import pingwit.beautysaloon.validator.OperationValidator;


import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    private static final String EXCEPTION_MESSAGE = "Operation not found: ";

    private final OperationRepository operationRepository;
    private final OperationConverter operationConverter;
    private final OperationValidator validator;

    public OperationServiceImpl(OperationRepository operationRepository, OperationConverter operationConverter, OperationValidator validator) {
        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
        this.validator = validator;
    }

    @Override
    public OperationDTO getOperationById(Integer id) {
        Operation operation = operationRepository.findById(id).orElseThrow(() -> new NotFoundException(EXCEPTION_MESSAGE + id));
        return operationConverter.convertOperationToDTO(operation);
    }

    @Override
    public List<OperationDTO> getAllOperations() {
        return operationConverter.convertOperationToDTO(operationRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createOperation(OperationDTO operationToCreate) {
        validator.validateOperation(operationToCreate);
        Operation operation = operationConverter.convertOperationToEntity(operationToCreate);
        Operation savedOperation = operationRepository.save(operation);
        return savedOperation.getId();
    }

    @Override
    public OperationDTO updateService(Integer id, OperationDTO operationToUpdate) {
        validator.validateOperation(operationToUpdate);
        Operation operation = operationRepository.findById(id).orElseThrow(() -> new NotFoundException(EXCEPTION_MESSAGE + id));
        Operation entityToUpdate = operationConverter.convertOperationToEntity(operationToUpdate);
        entityToUpdate.setId(id);
        Operation updatedOperation = operationRepository.save(entityToUpdate);
        return operationConverter.convertOperationToDTO(updatedOperation);
    }

    @Override
    @Transactional
    public void deleteOperation(Integer id) {
        Operation operation = operationRepository.findById(id).orElseThrow(() -> new NotFoundException(EXCEPTION_MESSAGE + id));
        operationRepository.delete(operation);
    }
}
