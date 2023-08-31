package pingwit.beautysaloon.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;
import pingwit.beautysaloon.repository.ClientRepository;
import pingwit.beautysaloon.repository.model.Client;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClientValidatorTest {
    private final ClientRepository clientRepository = mock(ClientRepository.class);

    private final ClientValidator target = new ClientValidator(clientRepository);

    @Test
    @DisplayName("Validation Error should not be thrown when input client is valid")
    void shouldNotThrow_whenClientIsValid() {
        //given
        ClientDTO valid = validClient();

        //when
        assertDoesNotThrow(() -> target.validateClient(valid));
        assertDoesNotThrow(() -> target.validateClientToUpdate(valid));

        //then
        verify(clientRepository).findByEmail(valid.getEmail());
    }

    @Test
    @DisplayName("Validation Error should be thrown when name is invalid")
    void shouldThrow_whenNameIsInvalid() {
        //given
        ClientDTO invalidNameClient = invalidNameClient();
        String expectedMessage = String.format("name can contain only letters: '%s'", invalidNameClient.getName());

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(invalidNameClient));
        BeautySalonValidationException actualForUpdate = assertThrows(BeautySalonValidationException.class, () -> target.validateClientToUpdate(invalidNameClient));

        //then
        assertThat(actual.getViolations()).containsOnly(expectedMessage);
        assertThat(actualForUpdate.getViolations()).containsOnly(expectedMessage);
    }


    @Test
    @DisplayName("Validation Error should be thrown when name is blank")
    void shouldThrow_whenNameIsBlank() {
        //given
        ClientDTO blankNameClient = blankNameClient();
        String expectedMessage = "name is blank";

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(blankNameClient));
        BeautySalonValidationException actualForUpdate = assertThrows(BeautySalonValidationException.class, () -> target.validateClientToUpdate(blankNameClient));

        //then
        assertThat(actual.getViolations()).contains(expectedMessage);
        assertThat(actualForUpdate.getViolations()).contains(expectedMessage);
    }


    @Test
    @DisplayName("Validation Error should be thrown when surname is invalid")
    void shouldThrow_whenSurnameIsInvalid() {
        //given
        ClientDTO invalidSurnameClient = invalidSurnameClient();
        String expectedMessage = String.format("surname can contain only letters: '%s'", invalidSurnameClient.getSurname());

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(invalidSurnameClient));
        BeautySalonValidationException actualForUpdate = assertThrows(BeautySalonValidationException.class, () -> target.validateClientToUpdate(invalidSurnameClient));

        //then
        assertThat(actual.getViolations()).containsOnly(expectedMessage);
        assertThat(actualForUpdate.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when surname is blank")
    void shouldThrow_whenSurnameIsBlank() {
        //given
        ClientDTO blankSurnameClient = blankSurnameClient();
        String expectedMessage = "surname is blank";

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(blankSurnameClient));
        BeautySalonValidationException actualForUpdate = assertThrows(BeautySalonValidationException.class, () -> target.validateClientToUpdate(blankSurnameClient));

        //then
        assertThat(actual.getViolations()).contains(expectedMessage);
        assertThat(actualForUpdate.getViolations()).contains(expectedMessage);
    }


    @Test
    @DisplayName("Validation Error should be thrown when phone is invalid")
    void shouldThrow_whenPhoneIsInvalid() {
        //given
        ClientDTO invalidPhoneClient = invalidPhoneClient();
        String expectedMessage = String.format("%s can contain only digits: '%s'", "phone", invalidPhoneClient.getPhone());

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(invalidPhoneClient));
        BeautySalonValidationException actualForUpdate = assertThrows(BeautySalonValidationException.class, () -> target.validateClientToUpdate(invalidPhoneClient));


        //then
        assertThat(actual.getViolations()).containsOnly(expectedMessage);
        assertThat(actualForUpdate.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when phone is blank")
    void shouldThrow_whenPhoneIsBlank() {
        //given
        ClientDTO blankPhoneClient = blankPhoneClient();
        String expectedMessage = "Phone is blank";

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(blankPhoneClient));
        BeautySalonValidationException actualForUpdate = assertThrows(BeautySalonValidationException.class, () -> target.validateClientToUpdate(blankPhoneClient));

        //then
        assertThat(actual.getViolations()).contains(expectedMessage);
        assertThat(actualForUpdate.getViolations()).contains(expectedMessage);
    }


    @Test
    @DisplayName("Validation Error should be thrown when email is invalid")
    void shouldThrow_whenEmailIsInvalid() {
        //given
        ClientDTO invalidEmailClient = invalidEmailClient();
        String expectedMessage = String.format("invalid email: '%s'", invalidEmailClient.getEmail());

        //when
        BeautySalonValidationException actual = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(invalidEmailClient));
        BeautySalonValidationException actualForUpdate = assertThrows(BeautySalonValidationException.class, () -> target.validateClientToUpdate(invalidEmailClient));

        //then
        assertThat(actual.getViolations()).containsOnly(expectedMessage);
        assertThat(actualForUpdate.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when email is already used")
    void shouldThrown_whenEmailAlreadyUsed() {
        //given
        ClientDTO client = validClient();
        String email = client.getEmail();
        when(clientRepository.findByEmail(email)).thenReturn(new Client());
        String expectedMessage = String.format("email '%s' is already used in the system. Please choose a different one!", client.getEmail());

        //when
        BeautySalonValidationException validationException = assertThrows(BeautySalonValidationException.class, () -> target.validateClient(client));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    private ClientDTO validClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("TestName");
        clientDTO.setSurname("TestSurname");
        clientDTO.setPhone("989898989");
        clientDTO.setEmail("test-email@gmail.com");
        clientDTO.setVip(true);
        return clientDTO;
    }

    private ClientDTO invalidNameClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("TestName1");
        clientDTO.setSurname("TestSurname");
        clientDTO.setPhone("989898989");
        clientDTO.setEmail("test-email@gmail.com");
        clientDTO.setVip(true);
        return clientDTO;
    }

    private ClientDTO blankNameClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(" ");
        clientDTO.setSurname("TestSurname");
        clientDTO.setPhone("989898989");
        clientDTO.setEmail("test-email@gmail.com");
        clientDTO.setVip(true);
        return clientDTO;
    }

    private ClientDTO invalidSurnameClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("TestName");
        clientDTO.setSurname("TestSurname1");
        clientDTO.setPhone("989898989");
        clientDTO.setEmail("test-email@gmail.com");
        clientDTO.setVip(true);
        return clientDTO;
    }

    private ClientDTO blankSurnameClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("TestName");
        clientDTO.setSurname("");
        clientDTO.setPhone("989898989");
        clientDTO.setEmail("test-email@gmail.com");
        clientDTO.setVip(true);
        return clientDTO;
    }

    private ClientDTO invalidPhoneClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("TestName");
        clientDTO.setSurname("TestSurname");
        clientDTO.setPhone("invalid_phone");
        clientDTO.setEmail("test-email@gmail.com");
        clientDTO.setVip(true);
        return clientDTO;
    }

    private ClientDTO blankPhoneClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("TestName");
        clientDTO.setSurname("TestSurname");
        clientDTO.setPhone("");
        clientDTO.setEmail("test-email@gmail.com");
        clientDTO.setVip(true);
        return clientDTO;
    }

    private ClientDTO invalidEmailClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("TestName");
        clientDTO.setSurname("TestSurname");
        clientDTO.setPhone("989898989");
        clientDTO.setEmail("invalid_email");
        clientDTO.setVip(true);
        return clientDTO;
    }
}