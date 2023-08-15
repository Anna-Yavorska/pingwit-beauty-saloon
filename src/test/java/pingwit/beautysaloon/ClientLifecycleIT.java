package pingwit.beautysaloon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pingwit.beautysaloon.controller.dto.ClientDTO;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("Tests client creation and subsequent retrieval")
    void verifyCreateClient(){
        //given
        RestTemplate restTemplate = new RestTemplate();
        ClientDTO someClient = someClient();

        // prepare request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientDTO> request = new HttpEntity<>(someClient, headers);

        // client creation
        ResponseEntity<ClientDTO> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/clients", request, ClientDTO.class);
        ClientDTO createdClient = forEntity.getBody();

        //when
        assert createdClient != null;
        ClientDTO actualClient = restTemplate.getForObject("http://localhost:" + port + "/clients/" + createdClient.getId(), ClientDTO.class);

        //then
        assertThat(actualClient).isNotNull();
        assertThat(actualClient.getName()).isEqualTo(someClient.getName());
        assertThat(actualClient.getSurname()).isEqualTo(someClient.getSurname());
        assertThat(actualClient.getPhone()).isEqualTo(someClient.getPhone());
        assertThat(actualClient.getEmail()).isEqualTo(someClient.getEmail());
        assertThat(actualClient.getVip()).isEqualTo(someClient.getVip());

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
}
