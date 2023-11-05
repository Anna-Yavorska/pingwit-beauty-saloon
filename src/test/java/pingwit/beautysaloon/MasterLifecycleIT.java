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
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest(classes = BeautySalonApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MasterLifecycleIT {
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
    @DisplayName("Check if any masters were created during startup")
    void checkClients() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MasterDTO[]> forEntity = restTemplate.getForEntity("http://localhost:" + port + "/masters", MasterDTO[].class);
        MasterDTO[] body = forEntity.getBody();

        assertThat(body).isNotEmpty();
    }

    @Test
    @DisplayName("Tests master creation and subsequent retrieval, update and removal")
    void verifyMasterLifecycle() {
        //given
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<BeautyProcedureDTO[]> forEntityArray = restTemplate.getForEntity("http://localhost:" + port + "/procedures", BeautyProcedureDTO[].class);
        List<BeautyProcedureDTO> procedures = Arrays.stream(Objects.requireNonNull(forEntityArray.getBody())).toList();

        MasterDTO someMaster = someMaster();
        someMaster.setProcedures(procedures);

        MasterDTO updateMaster = updateMaster();
        updateMaster.setProcedures(procedures);

        // prepare request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // security
        String auth = "admin" + ":" + "superman";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.add("Authorization", authHeader);

        HttpEntity<MasterDTO> request = new HttpEntity<>(someMaster, headers);

        // master creation
        ResponseEntity<Integer> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/masters", request, Integer.class);
        Integer createdMasterId = forEntity.getBody();

        //when
        MasterDTO actualMaster = restTemplate.getForObject("http://localhost:" + port + "/masters/" + createdMasterId, MasterDTO.class);

        //create master then
        assertThat(actualMaster).usingRecursiveComparison().ignoringFields("id").isEqualTo(someMaster);

        //update master
        HttpEntity<MasterDTO> requestUpdate = new HttpEntity<>(updateMaster, headers);
        assert actualMaster != null;
        actualMaster.setId(createdMasterId);
        ResponseEntity<MasterDTO> updatedMaster = restTemplate.exchange("http://localhost:" + port + "/masters/" + createdMasterId, HttpMethod.PUT, requestUpdate, MasterDTO.class);
        MasterDTO updatedMasterBody = updatedMaster.getBody();

        //update master then
        assertThat(updatedMasterBody).usingRecursiveComparison().ignoringFields("id").isEqualTo(updateMaster);

        //delete master
        restTemplate.exchange("http://localhost:" + port + "/masters/" + createdMasterId, HttpMethod.DELETE, request, MasterDTO.class);
        HttpClientErrorException.NotFound actualException = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForObject("http://localhost:" + port + "/masters/" + createdMasterId, MasterDTO.class));

        String expectedMessage = String.format("404 : \"Master not found: %d\"", createdMasterId);

        //delete master then
        assertThat(actualException.getMessage()).isEqualTo(expectedMessage);
    }

    private MasterDTO someMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("TestSurname");
        master.setPhone("123456789");
        master.setProfLevel("middle");
        master.setProfession("manicurist");
        master.setProcedures(null);
        return master;
    }

    private MasterDTO updateMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("UpdateName");
        master.setSurname("UpdateSurname");
        master.setPhone("987654321");
        master.setProfLevel("basic");
        master.setProfession("manicurist");
        master.setProcedures(null);
        return master;
    }
}
