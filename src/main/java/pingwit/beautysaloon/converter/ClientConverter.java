package pingwit.beautysaloon.converter;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.repository.model.Client;

import java.util.Collection;
import java.util.List;

@Component
public class ClientConverter {
    public Client convertClientToEntity(ClientDTO source) {
        return new Client(
                source.getId(),
                source.getName(),
                source.getSurname(),
                source.getPhone(),
                source.getEmail(),
                source.getVip()
        );
    }

    public ClientDTO convertClientToDTO(Client source) {
        return convertToDto(source);
    }

    public List<ClientDTO> convertClientToDTO(Collection<Client> source) {
        return source.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ClientDTO convertToDto(Client source) {
        ClientDTO result = new ClientDTO();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setSurname(source.getSurname());
        result.setPhone(source.getPhone());
        result.setEmail(source.getEmail());
        result.setVip(source.getVip());
        return result;
    }
}
