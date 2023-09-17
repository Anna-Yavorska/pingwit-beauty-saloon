package pingwit.beautysaloon;

import org.apache.commons.codec.binary.Base64;
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
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest(classes = BeautySalonApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeautyProcedureLifecycleIT {
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
    @DisplayName("Check if any procedures were created during startup")
    void checkClients() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<BeautyProcedureDTO[]> forEntity = restTemplate.getForEntity("http://localhost:" + port + "/procedures", BeautyProcedureDTO[].class);
        BeautyProcedureDTO[] body = forEntity.getBody();

        assertThat(body).isNotEmpty();
    }

    @Test
    @DisplayName("Tests procedure creation and subsequent retrieval, update and removal")
    void verifyProcedureLifecycle() {
        //given
        RestTemplate restTemplate = new RestTemplate();
        BeautyProcedureDTO someProcedure = someProcedure();
        BeautyProcedureDTO updateProcedure = updateProcedure();

        // prepare request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // security
        String auth = "admin" + ":" + "superman";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.add("Authorization", authHeader);

        HttpEntity<BeautyProcedureDTO> request = new HttpEntity<>(someProcedure, headers);

        // procedure creation
        ResponseEntity<Integer> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/procedures", request, Integer.class);
        Integer createdProcedureId = forEntity.getBody();

        //when
        BeautyProcedureDTO actualProcedure = restTemplate.getForObject("http://localhost:" + port + "/procedures/" + createdProcedureId, BeautyProcedureDTO.class);

        //update procedure
        HttpEntity<BeautyProcedureDTO> requestUpdate = new HttpEntity<>(updateProcedure, headers);
        updateProcedure.setId(createdProcedureId);
        ResponseEntity<BeautyProcedureDTO> updatedProcedure = restTemplate.exchange("http://localhost:" + port + "/procedures/" + createdProcedureId, HttpMethod.PUT, requestUpdate, BeautyProcedureDTO.class);
        BeautyProcedureDTO updatedProcedureBody = updatedProcedure.getBody();

        //delete procedure
        restTemplate.exchange("http://localhost:" + port + "/procedures/" + createdProcedureId, HttpMethod.DELETE, request, BeautyProcedureDTO.class);
        HttpClientErrorException.NotFound actualException = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForObject("http://localhost:" + port + "/procedures/" + createdProcedureId, BeautyProcedureDTO.class));

        String expectedMessage = String.format("404 : \"Procedure not found: %d\"", createdProcedureId);

        //create procedure then
        assertThat(actualProcedure).usingRecursiveComparison().ignoringFields("id").isEqualTo(someProcedure);

        //update procedure then
        assertThat(updatedProcedureBody).usingRecursiveComparison().ignoringFields("id").isEqualTo(updateProcedure);

        //delete procedure then
        assertThat(actualException.getMessage()).isEqualTo(expectedMessage);
    }

    private BeautyProcedureDTO someProcedure() {
        BeautyProcedureDTO procedure = new BeautyProcedureDTO();
        procedure.setName("SomeName");
        procedure.setDescription("Some description about procedure");
        procedure.setTime(new BigDecimal("1.50"));
        return procedure;
    }

    private BeautyProcedureDTO updateProcedure() {
        BeautyProcedureDTO procedure = new BeautyProcedureDTO();
        procedure.setName("UpdateName");
        procedure.setDescription("Some description about procedure");
        procedure.setTime(new BigDecimal("1.00"));
        return procedure;
    }
}
