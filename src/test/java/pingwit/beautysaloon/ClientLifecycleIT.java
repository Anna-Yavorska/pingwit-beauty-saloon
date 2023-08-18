package pingwit.beautysaloon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pingwit.beautysaloon.controller.dto.ClientDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest(classes = BeautySaloonApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientLifecycleIT {
    @LocalServerPort
    private Integer port;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getPassword);
        registry.add("spring.datasource.password", postgres::getUsername);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }
    @Test
    @DisplayName("Check if any clients were created during startup")
    void checkClients(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<ClientDTO[]> forEntity = restTemplate.getForEntity("http://localhost:" + port + "/clients", ClientDTO[].class);
        ClientDTO[] body = forEntity.getBody();

        assertThat(body).isNotEmpty();
    }

    @Test
    @DisplayName("Tests client creation and subsequent retrieval, update and removal")
    void verifyClientLifecycle(){
        //given
        RestTemplate restTemplate = new RestTemplate();
        ClientDTO someClient = someClient();
        ClientDTO updateClient = updateClient();
        String updatedClientName = updateClient.getName();
        String updatedClientPhone = updateClient.getPhone();
        String updatedClientEmail = updateClient.getEmail();

        // prepare request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientDTO> request = new HttpEntity<>(someClient, headers);
        HttpEntity<ClientDTO> requestUpdate = new HttpEntity<>(updateClient, headers);

        // client creation
        ResponseEntity<Integer> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/clients", request, Integer.class);
        Integer createdClientId = forEntity.getBody();

        //when
        ClientDTO actualClient = restTemplate.getForObject("http://localhost:" + port + "/clients/" + createdClientId, ClientDTO.class);

        //update client
        updateClient.setId(createdClientId);
        ResponseEntity<ClientDTO> updatedClient = restTemplate.exchange("http://localhost:" + port + "/clients/" + createdClientId, HttpMethod.PUT, requestUpdate, ClientDTO.class);
        ClientDTO updatedClientBody = updatedClient.getBody();


        //delete client
        restTemplate.delete("http://localhost:" + port + "/clients/" + createdClientId);


        HttpClientErrorException.NotFound actualException = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForObject("http://localhost:" + port + "/clients/" + createdClientId, ClientDTO.class));

        String expectedMessage =String.format("404 : \"Client not found: %d\"", createdClientId);


        //create client then
        assertThat(actualClient).isNotNull();
        assertThat(actualClient.getName()).isEqualTo(someClient.getName());
        assertThat(actualClient.getSurname()).isEqualTo(someClient.getSurname());
        assertThat(actualClient.getPhone()).isEqualTo(someClient.getPhone());
        assertThat(actualClient.getEmail()).isEqualTo(someClient.getEmail());
        assertThat(actualClient.getVip()).isEqualTo(someClient.getVip());

        //update client then
        assert updatedClientBody != null;
        assertThat(updatedClientBody.getName()).isEqualTo(updatedClientName);
        assertThat(updatedClientBody.getPhone()).isEqualTo(updatedClientPhone);
        assertThat(updatedClientBody.getEmail()).isEqualTo(updatedClientEmail);


        //delete client then
        assertThat(actualException.getMessage()).isEqualTo(expectedMessage);

    }

    private ClientDTO someClient() {
        ClientDTO client = new ClientDTO();
        client.setName("ClientName");
        client.setSurname("ClientSurname");
        client.setPhone("121212111");
        client.setEmail("client@gmail.com");
        client.setVip(true);
        return client;
    }
    private ClientDTO updateClient(){
        ClientDTO client = new ClientDTO();
        client.setName("UpdateName");
        client.setSurname("ClientSurname");
        client.setPhone("999999999");
        client.setEmail("client@gmail.com");
        client.setVip(true);
        return client;
    }
}
