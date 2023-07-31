package pingwit.beautysaloon.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.converter.ClientConverter;
import pingwit.beautysaloon.exception.NotFoundException;
import pingwit.beautysaloon.repositiry.ClientRepository;
import pingwit.beautysaloon.repositiry.model.Client;
import pingwit.beautysaloon.service.ClientService;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;

    public ClientServiceImpl(ClientRepository clientRepository, ClientConverter clientConverter) {
        this.clientRepository = clientRepository;
        this.clientConverter = clientConverter;
    }


    @Override
    public ClientDTO getClientById(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found: " + id));
        return clientConverter.convertClientToDTO(client);
    }

    @Override
    public Collection<ClientDTO> getAllClients() {
        return clientConverter.convertClientToDTO(clientRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createClient(ClientDTO clientToCreate) {
        Client client = clientConverter.convertClientToEntity(clientToCreate);
        Client savedClient = clientRepository.save(client);
        return savedClient.getId();
    }

    @Override
    @Transactional
    public ClientDTO updateClient(Integer id, ClientDTO clientToUpdate) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found: " + id));
        Client entityToUpdate = clientConverter.convertClientToEntity(clientToUpdate);
        entityToUpdate.setId(id);
        Client updatedEntity = clientRepository.save(entityToUpdate);
        return clientConverter.convertClientToDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteClient(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found: " + id));
        clientRepository.delete(client);
    }
}
