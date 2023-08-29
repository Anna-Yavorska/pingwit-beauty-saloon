package pingwit.beautysaloon.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.exception.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MasterValidatorTest {
    private final MasterValidator target = new MasterValidator();

    @Test
    @DisplayName("Validation Error should not be thrown when input master is valid")
    void shouldNotThrow_whenMasterIsValid() {
        //given
        MasterDTO valid = validMaster();

        //when
        assertDoesNotThrow(() -> target.validateMaster(valid));

        //then
    }

    @Test
    @DisplayName("Validation Error should be thrown when name is invalid")
    void shouldThrow_whenNameIsInvalid() {
        //given
        MasterDTO invalidNameMaster = invalidNameMaster();
        String expectedMessage = String.format("name can contain only letters: '%s'", invalidNameMaster.getName());

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(invalidNameMaster));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when name is blank")
    void shouldThrow_whenNameIsBlank() {
        //given
        MasterDTO blankNameMaster = blankNameMaster();
        String expectedMessage = "name is blank";

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(blankNameMaster));

        //then
        assertThat(validationException.getViolations()).contains(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when surname is invalid")
    void shouldThrow_whenSurnameIsInvalid() {
        //given
        MasterDTO invalidSurnameMaster = invalidSurnameMaster();
        String expectedMessage = String.format("surname can contain only letters: '%s'", invalidSurnameMaster.getSurname());

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(invalidSurnameMaster));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when surname is blank")
    void shouldThrow_whenSurnameIsBlank() {
        //given
        MasterDTO blankSurnameMaster = blankSurnameMaster();
        String expectedMessage = "surname is blank";

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(blankSurnameMaster));

        //then
        assertThat(validationException.getViolations()).contains(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when phone is invalid")
    void shouldThrow_whenPhoneIsInvalid() {
        //given
        MasterDTO invalidPhoneMaster = invalidPhoneMaster();
        String expectedMessage = String.format("%s can contain only digits: '%s'", "phone", invalidPhoneMaster.getPhone());

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(invalidPhoneMaster));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when phone is blank")
    void shouldThrow_whenPhoneIsBlank() {
        //given
        MasterDTO blankPhoneMaster = blankPhoneMaster();
        String expectedMessage = "Phone is blank";

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(blankPhoneMaster));

        //then
        assertThat(validationException.getViolations()).contains(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when ProfLevel is invalid")
    void shouldThrow_whenProfLevelIsInvalid() {
        //given
        MasterDTO invalidProfLevelMaster = invalidProfLevelMaster();
        String expectedMessage = String.format("'%s' doesn't exist at system", invalidProfLevelMaster.getProfLevel());

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(invalidProfLevelMaster));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }

    @Test
    @DisplayName("Validation Error should be thrown when Profession is invalid")
    void shouldThrow_whenProfessionIsInvalid() {
        //given
        MasterDTO invalidProfessionMaster = invalidProfessionMaster();
        String expectedMessage = String.format("'%s' doesn't exist at system", invalidProfessionMaster.getProfession());

        //when
        ValidationException validationException = assertThrows(ValidationException.class, () -> target.validateMaster(invalidProfessionMaster));

        //then
        assertThat(validationException.getViolations()).containsOnly(expectedMessage);
    }


    private MasterDTO validMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("TestSurname");
        master.setPhone("111111111");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO invalidNameMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName1");
        master.setSurname("TestSurname");
        master.setPhone("111111111");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO blankNameMaster() {
        MasterDTO master = new MasterDTO();
        master.setName(" ");
        master.setSurname("TestSurname");
        master.setPhone("111111111");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO invalidSurnameMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("TestSurname!");
        master.setPhone("111111111");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO blankSurnameMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("");
        master.setPhone("111111111");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO invalidPhoneMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("TestSurname");
        master.setPhone("test_phone");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO blankPhoneMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("TestSurname");
        master.setPhone("");
        master.setProfLevel("senior");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO invalidProfLevelMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("TestSurname");
        master.setPhone("111111111");
        master.setProfLevel("testLevel");
        master.setProfession("hairdresser");
        return master;
    }

    private MasterDTO invalidProfessionMaster() {
        MasterDTO master = new MasterDTO();
        master.setName("TestName");
        master.setSurname("TestSurname");
        master.setPhone("111111111");
        master.setProfLevel("senior");
        master.setProfession("testProfession");
        return master;
    }
}