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
import pingwit.beautysaloon.controller.dto.MasterDTO;

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
    static void postgresProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getPassword);
        registry.add("spring.datasource.password", postgres::getUsername);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }
    @Test
    @DisplayName("Check if any masters were created during startup")
    void checkClients(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<MasterDTO[]> forEntity = restTemplate.getForEntity("http://localhost:" + port + "/masters", MasterDTO[].class);
        MasterDTO[] body = forEntity.getBody();

        assertThat(body).isNotEmpty();
    }
    @Test
    @DisplayName("Tests master creation and subsequent retrieval, update and removal")
    void verifyMasterLifecycle(){
        //given
        RestTemplate restTemplate = new RestTemplate();
        MasterDTO someMaster = someMaster();
        MasterDTO updateMaster = updateMaster();

        String updatedName = updateMaster.getName();
        String updatedSurname = updateMaster.getSurname();
        String updatedPhone = updateMaster.getPhone();
        String updatedProfLevel = updateMaster.getProfLevel();
        String updatedProfession = updateMaster.getProfession();

        // prepare request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MasterDTO> request = new HttpEntity<>(someMaster, headers);
        HttpEntity<MasterDTO> requestUpdate = new HttpEntity<>(updateMaster, headers);

        // master creation
        ResponseEntity<Integer> forEntity = restTemplate.postForEntity("http://localhost:" + port + "/masters", request, Integer.class);
        Integer createdMasterId = forEntity.getBody();

        //when
        MasterDTO actualMaster = restTemplate.getForObject("http://localhost:" + port + "/masters/" + createdMasterId, MasterDTO.class);

        //update master
        assert actualMaster != null;
        actualMaster.setId(createdMasterId);
        ResponseEntity<MasterDTO> updatedMaster = restTemplate.exchange("http://localhost:" + port + "/masters/" + createdMasterId, HttpMethod.PUT, requestUpdate, MasterDTO.class);
        MasterDTO updatedMasterBody = updatedMaster.getBody();

        //delete master
        restTemplate.delete("http://localhost:" + port + "/masters/" + createdMasterId);
        HttpClientErrorException.NotFound actualException = assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.getForObject("http://localhost:" + port + "/masters/" + createdMasterId, MasterDTO.class));

        String expectedMessage = String.format("404 : \"Master not found: %d\"", createdMasterId);

        //create master then
        assertThat(actualMaster).isNotNull();
        assertThat(actualMaster.getName()).isEqualTo(someMaster.getName());
        assertThat(actualMaster.getSurname()).isEqualTo(someMaster.getSurname());
        assertThat(actualMaster.getPhone()).isEqualTo(someMaster.getPhone());
        assertThat(actualMaster.getProfLevel()).isEqualTo(someMaster.getProfLevel());
        assertThat(actualMaster.getProfession()).isEqualTo(someMaster.getProfession());

        //update master then
        assert updatedMasterBody != null;
        assertThat(updatedMasterBody.getName()).isEqualTo(updatedName);
        assertThat(updatedMasterBody.getSurname()).isEqualTo(updatedSurname);
        assertThat(updatedMasterBody.getPhone()).isEqualTo(updatedPhone);
        assertThat(updatedMasterBody.getProfLevel()).isEqualTo(updatedProfLevel);
        assertThat(updatedMasterBody.getProfession()).isEqualTo(updatedProfession);


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
