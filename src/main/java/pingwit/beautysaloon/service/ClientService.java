package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.ClientDTO;

import java.util.Collection;

public interface ClientService {
    ClientDTO getClientById(Integer id);

    Collection<ClientDTO> getAllClients();

    Integer createClient(ClientDTO clientToCreate);

    ClientDTO updateClient(Integer id, ClientDTO clientToUpdate);

    void deleteClient(Integer id);
}
