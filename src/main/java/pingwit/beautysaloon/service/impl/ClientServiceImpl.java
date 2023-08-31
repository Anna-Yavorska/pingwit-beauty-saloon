package pingwit.beautysaloon.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.converter.ClientConverter;
import pingwit.beautysaloon.exception.BeautySalonNotFoundException;
import pingwit.beautysaloon.repository.ClientRepository;
import pingwit.beautysaloon.repository.model.Client;
import pingwit.beautysaloon.service.ClientService;
import pingwit.beautysaloon.validator.ClientValidator;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private static final String EXCEPTION_MESSAGE = "Client not found: ";

    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;
    private final ClientValidator validator;

    public ClientServiceImpl(ClientRepository clientRepository, ClientConverter clientConverter, ClientValidator validator) {
        this.clientRepository = clientRepository;
        this.clientConverter = clientConverter;
        this.validator = validator;
    }


    @Override
    public ClientDTO getClientById(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        return clientConverter.convertClientToDTO(client);
    }

    @Override
    public Collection<ClientDTO> getAllClients() {
        return clientConverter.convertClientToDTO(clientRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createClient(ClientDTO clientToCreate) {
        validator.validateClient(clientToCreate);
        Client client = clientConverter.convertClientToEntity(clientToCreate);
        Client savedClient = clientRepository.save(client);
        return savedClient.getId();
    }

    @Override
    @Transactional
    public ClientDTO updateClient(Integer id, ClientDTO clientToUpdate) {
        validator.validateClientToUpdate(clientToUpdate);
        if (clientRepository.findById(id).isEmpty()) {
            throw new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id);
        }
        Client entityToUpdate = clientConverter.convertClientToEntity(clientToUpdate);
        entityToUpdate.setId(id);
        Client updatedEntity = clientRepository.save(entityToUpdate);
        return clientConverter.convertClientToDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteClient(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new BeautySalonNotFoundException(EXCEPTION_MESSAGE + id));
        clientRepository.delete(client);
    }
}
