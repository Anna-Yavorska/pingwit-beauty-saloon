package pingwit.beautysaloon;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest(classes = BeautySalonApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientLifecycleIT {
    @LocalServerPort
    private Integer port;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getPassword);
        registry.add("spring.datasource.password", postgres::getUsername);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }

    @Test
    @DisplayName("Tests client creation and subsequent retrieval, update and removal")
    void verifyClientLifecycle() {
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

        // security
        String auth = "admin" + ":" + "superman";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.add("Authorization", authHeader);

        HttpEntity<ClientDTO> request = new HttpEntity<>(someClient, headers);

        //Check if any clients were created during startup
        ResponseEntity<ClientDTO[]> forEntityArray = restTemplate.exchange("http://localhost:" + port + "/clients", HttpMethod.GET, request, ClientDTO[].class);
        ClientDTO[] clients = forEntityArray.getBody();
        assertThat(clients).isNotEmpty();

        // client creation
        ResponseEntity<Integer> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/clients", request, Integer.class);
        Integer createdClientId = forEntity.getBody();

        //when
        ResponseEntity<ClientDTO> actual = restTemplate.exchange("http://localhost:" + port + "/clients/" + createdClientId, HttpMethod.GET, request, ClientDTO.class);
        ClientDTO actualClient = actual.getBody();

        //create client then
        assertThat(actualClient).isNotNull();
        assertThat(actualClient.getName()).isEqualTo(someClient.getName());
        assertThat(actualClient.getSurname()).isEqualTo(someClient.getSurname());
        assertThat(actualClient.getPhone()).isEqualTo(someClient.getPhone());
        assertThat(actualClient.getEmail()).isEqualTo(someClient.getEmail());
        assertThat(actualClient.getVip()).isEqualTo(someClient.getVip());

        //update client
        HttpEntity<ClientDTO> requestUpdate = new HttpEntity<>(updateClient, headers);
        updateClient.setId(createdClientId);
        ResponseEntity<ClientDTO> updatedClient = restTemplate.exchange("http://localhost:" + port + "/clients/" + createdClientId, HttpMethod.PUT, requestUpdate, ClientDTO.class);
        ClientDTO updatedClientBody = updatedClient.getBody();

        //update client then
        assert updatedClientBody != null;
        assertThat(updatedClientBody.getName()).isEqualTo(updatedClientName);
        assertThat(updatedClientBody.getPhone()).isEqualTo(updatedClientPhone);
        assertThat(updatedClientBody.getEmail()).isEqualTo(updatedClientEmail);

        //delete client
        restTemplate.exchange("http://localhost:" + port + "/clients/" + createdClientId, HttpMethod.DELETE, request, ClientDTO.class);

        HttpClientErrorException.NotFound actualException = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.exchange("http://localhost:" + port + "/clients/" + createdClientId, HttpMethod.GET, request, ClientDTO.class));

        String expectedMessage = String.format("404 : \"Client not found: %d\"", createdClientId);

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

    private ClientDTO updateClient() {
        ClientDTO client = new ClientDTO();
        client.setName("UpdateName");
        client.setSurname("ClientSurname");
        client.setPhone("999999999");
        client.setEmail("client@gmail.com");
        client.setVip(true);
        return client;
    }
}
