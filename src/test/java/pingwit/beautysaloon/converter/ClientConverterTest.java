package pingwit.beautysaloon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.repositiry.model.Client;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientConverterTest {
    private static final String NAME = "TestName";
    private static final String SURNAME = "TestSurname";
    private static final String PHONE = "911";
    private static final String EMAIL = "test@email.com";
    private static final Boolean IS_VIP = false;
    private static final Integer ID = 1111;
    private final ClientConverter target = new ClientConverter();

    @Test
    @DisplayName("Should convert ClientDTO to Client")
    void shouldConvertClientDtoToEntity(){
        //given
        ClientDTO client = clientDTO(ID);
        Client expected = entityClient(ID);

        //when
        Client actual = target.convertClientToEntity(client);

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getSurname()).isEqualTo(expected.getSurname());
        assertThat(actual.getPhone()).isEqualTo(expected.getPhone());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getVip()).isEqualTo(expected.getVip());

    }
    @Test
    @DisplayName("Should convert Client to DTO")
    void shouldConvertClientToDto() {
        //given
        Client client = entityClient(ID);
        ClientDTO expected = clientDTO(ID);

        //when
        ClientDTO actual = target.convertClientToDTO(client);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should convert Collection<Clients> to List<ClientDTO>")
    void shouldConvertCollectionClientsToListClientsDTO(){
        //given
        Collection<Client> clients = List.of(entityClient(ID));
        List<ClientDTO> expected = List.of(clientDTO(ID));

        //when
        List<ClientDTO> actual = target.convertClientToDTO(clients);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private Client entityClient(Integer id) {
        Client client = new Client();
        client.setId(id);
        client.setName(NAME);
        client.setSurname(SURNAME);
        client.setPhone(PHONE);
        client.setEmail(EMAIL);
        client.setVip(IS_VIP);
        return client;
    }
    private ClientDTO clientDTO(Integer id) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(id);
        clientDTO.setName(NAME);
        clientDTO.setSurname(SURNAME);
        clientDTO.setPhone(PHONE);
        clientDTO.setEmail(EMAIL);
        clientDTO.setVip(IS_VIP);
        return clientDTO;
    }
}