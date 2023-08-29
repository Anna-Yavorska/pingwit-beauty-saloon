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
import pingwit.beautysaloon.controller.dto.ProcedureDTO;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest(classes = BeautySalonApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProcedureLifecycleIT {
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
        ResponseEntity<ProcedureDTO[]> forEntity = restTemplate.getForEntity("http://localhost:" + port + "/procedures", ProcedureDTO[].class);
        ProcedureDTO[] body = forEntity.getBody();

        assertThat(body).isNotEmpty();
    }

    @Test
    @DisplayName("Tests procedure creation and subsequent retrieval, update and removal")
    void verifyProcedureLifecycle() {
        //given
        RestTemplate restTemplate = new RestTemplate();
        ProcedureDTO someProcedure = someProcedure();
        ProcedureDTO updateProcedure = updateProcedure();
        String updatedProcedureName = updateProcedure.getName();
        String updatedProcedureDescription = updateProcedure.getDescription();
        BigDecimal updatedProcedureTime = updateProcedure.getTime();

        // prepare request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // security
        String auth = "admin" + ":" + "superman";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.add("Authorization", authHeader);

        HttpEntity<ProcedureDTO> request = new HttpEntity<>(someProcedure, headers);

        // procedure creation
        ResponseEntity<Integer> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/procedures", request, Integer.class);
        Integer createdProcedureId = forEntity.getBody();

        //when
        ProcedureDTO actualProcedure = restTemplate.getForObject("http://localhost:" + port + "/procedures/" + createdProcedureId, ProcedureDTO.class);

        //update procedure
        HttpEntity<ProcedureDTO> requestUpdate = new HttpEntity<>(updateProcedure, headers);
        updateProcedure.setId(createdProcedureId);
        ResponseEntity<ProcedureDTO> updatedProcedure = restTemplate.exchange("http://localhost:" + port + "/procedures/" + createdProcedureId, HttpMethod.PUT, requestUpdate, ProcedureDTO.class);
        ProcedureDTO updatedProcedureBody = updatedProcedure.getBody();

        //delete procedure
        restTemplate.exchange("http://localhost:" + port + "/procedures/" + createdProcedureId, HttpMethod.DELETE, request, ProcedureDTO.class);
        HttpClientErrorException.NotFound actualException = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForObject("http://localhost:" + port + "/procedures/" + createdProcedureId, ProcedureDTO.class));

        String expectedMessage = String.format("404 : \"Procedure not found: %d\"", createdProcedureId);

        //create procedure then
        assertThat(actualProcedure).isNotNull();
        assertThat(actualProcedure.getName()).isEqualTo(someProcedure.getName());
        assertThat(actualProcedure.getDescription()).isEqualTo(someProcedure.getDescription());
        assertThat(actualProcedure.getTime()).isEqualTo(someProcedure.getTime());

        //update procedure then
        assert updatedProcedureBody != null;
        assertThat(updatedProcedureBody.getName()).isEqualTo(updatedProcedureName);
        assertThat(updatedProcedureBody.getDescription()).isEqualTo(updatedProcedureDescription);
        assertThat(updatedProcedureBody.getTime()).isEqualTo(updatedProcedureTime);


        //delete procedure then
        assertThat(actualException.getMessage()).isEqualTo(expectedMessage);
    }

    private ProcedureDTO someProcedure() {
        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("SomeName");
        procedure.setDescription("Some description about procedure");
        procedure.setTime(new BigDecimal("1.50"));
        return procedure;
    }

    private ProcedureDTO updateProcedure() {
        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("UpdateName");
        procedure.setDescription("Some description about procedure");
        procedure.setTime(new BigDecimal("1.00"));
        return procedure;
    }
}
