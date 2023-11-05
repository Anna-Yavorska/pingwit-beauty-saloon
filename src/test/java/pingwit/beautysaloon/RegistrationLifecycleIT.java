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
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.RegistrationDTO;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest(classes = BeautySalonApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationLifecycleIT {
    private static final Integer VALID_ID = 1;
    private static final Integer UPDATE_ID = 2;
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
    @DisplayName("Tests operation creation and subsequent retrieval, update and removal")
    void verifyOperationLifecycle() {
        //given
        RestTemplate restTemplate = new RestTemplate();
        RegistrationDTO someOperation = someOperation();
        RegistrationDTO updateOperation = updateOperation();

        // prepare request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // security
        String auth = "admin" + ":" + "superman";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.add("Authorization", authHeader);

        HttpEntity<RegistrationDTO> request = new HttpEntity<>(someOperation, headers);

        //Check if any operation were created during startup
        ResponseEntity<RegistrationDTO[]> forEntityArray = restTemplate.exchange("http://localhost:" + port + "/operations", HttpMethod.GET, request, RegistrationDTO[].class);
        RegistrationDTO[] operations = forEntityArray.getBody();
        assertThat(operations).isNotEmpty();

        //prepare valid operation
        ResponseEntity<ClientDTO> validClient = restTemplate.exchange("http://localhost:" + port + "/clients/" + VALID_ID, HttpMethod.GET, request, ClientDTO.class);
        MasterDTO validMaster = restTemplate.getForObject("http://localhost:" + port + "/masters/" + VALID_ID, MasterDTO.class);
        BeautyProcedureDTO validProcedure = restTemplate.getForObject("http://localhost:" + port + "/procedures/" + VALID_ID, BeautyProcedureDTO.class);
        someOperation.setClient(validClient.getBody());
        someOperation.setMaster(validMaster);
        someOperation.setProcedure(validProcedure);

        //create operation
        ResponseEntity<Integer> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/operations", request, Integer.class);
        Integer createdOperationId = forEntity.getBody();

        //when
        ResponseEntity<RegistrationDTO> actual = restTemplate.exchange("http://localhost:" + port + "/operations/" + createdOperationId, HttpMethod.GET, request, RegistrationDTO.class);
        RegistrationDTO actualOperation = actual.getBody();

        //create operation then
        assertThat(actualOperation).usingRecursiveComparison().ignoringFields("id").isEqualTo(someOperation);

        //prepare operation for update
        ResponseEntity<ClientDTO> updateClient = restTemplate.exchange("http://localhost:" + port + "/clients/" + UPDATE_ID, HttpMethod.GET, request, ClientDTO.class);
        MasterDTO updateMaster = restTemplate.getForObject("http://localhost:" + port + "/masters/" + VALID_ID, MasterDTO.class);
        BeautyProcedureDTO updateProcedure = restTemplate.getForObject("http://localhost:" + port + "/procedures/" + VALID_ID, BeautyProcedureDTO.class);
        updateOperation.setId(createdOperationId);
        updateOperation.setClient(updateClient.getBody());
        updateOperation.setMaster(updateMaster);
        updateOperation.setProcedure(updateProcedure);

        //update operation
        HttpEntity<RegistrationDTO> requestUpdate = new HttpEntity<>(updateOperation, headers);
        ResponseEntity<RegistrationDTO> updatedOperation = restTemplate.exchange("http://localhost:" + port + "/operations/" + createdOperationId, HttpMethod.PUT, requestUpdate, RegistrationDTO.class);
        RegistrationDTO updatedOperationBody = updatedOperation.getBody();

        //updating operation then
        assertThat(updatedOperationBody).usingRecursiveComparison().ignoringFields("id").isEqualTo(updateOperation);

        //delete operation
        restTemplate.exchange("http://localhost:" + port + "/operations/" + createdOperationId, HttpMethod.DELETE, request, RegistrationDTO.class);

        HttpClientErrorException.NotFound actualException = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.exchange("http://localhost:" + port + "/operations/" + createdOperationId, HttpMethod.GET, request, RegistrationDTO.class));

        String expectedMessage = String.format("404 : \"Operation not found: %d\"", createdOperationId);

        //delete client then
        assertThat(actualException.getMessage()).isEqualTo(expectedMessage);
    }

    private RegistrationDTO someOperation() {
        RegistrationDTO operation = new RegistrationDTO();
        operation.setName("TestName");
        operation.setClient(null);
        operation.setMaster(null);
        operation.setDate(Date.valueOf("2023-09-01"));
        operation.setProcedure(null);
        operation.setPrice(new BigDecimal("5.50"));
        return operation;
    }

    private RegistrationDTO updateOperation() {
        RegistrationDTO operation = new RegistrationDTO();
        operation.setName("UpdateName");
        operation.setClient(null);
        operation.setMaster(null);
        operation.setDate(Date.valueOf("2023-12-29"));
        operation.setProcedure(null);
        operation.setPrice(new BigDecimal("25.90"));
        return operation;
    }
}
