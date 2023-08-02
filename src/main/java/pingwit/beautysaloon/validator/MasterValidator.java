package pingwit.beautysaloon.validator;

import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.exception.ValidationException;
import pingwit.beautysaloon.repositiry.model.ProfLevel;
import pingwit.beautysaloon.repositiry.model.Profession;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class MasterValidator {
    private static final Pattern ONLY_LETTERS_PATTERN = Pattern.compile("^[a-zA-Z]*$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d+");

    public void validateMaster(MasterDTO masterDTO) {
        List<String> violations = new ArrayList<>();
        validateLetterField(masterDTO.getName(), "name", violations);
        validateLetterField(masterDTO.getSurname(), "surname", violations);
        validatePhone(masterDTO, violations);
        validateProfLevel(masterDTO, violations);
        validateProfession(masterDTO, violations);
        if (!violations.isEmpty()) {
            throw new ValidationException("Provided client is invalid!", violations);
        }
    }

    private static void validateLetterField(String value, String fieldName, List<String> violations) {
        if (isBlank(value)) {
            violations.add(String.format("%s is blank", fieldName));
        }
        if (!ONLY_LETTERS_PATTERN.matcher(value).matches()) {
            violations.add(String.format("%s can contain only letters: '%s'", fieldName, value));
        }
    }

    private static void validatePhone(MasterDTO masterDTO, List<String> violations) {
        if (isBlank(masterDTO.getPhone())) {
            violations.add("Phone is blank");
        }
        if (!PHONE_NUMBER_PATTERN.matcher(masterDTO.getPhone()).matches()) {
            violations.add(String.format("%s can contain only digits: '%s'", "phone", masterDTO.getPhone()));
        }
    }

    private void validateProfLevel(MasterDTO masterDTO, List<String> violations) {
        for (ProfLevel value : ProfLevel.values()) {
            if (!value.hasValue(masterDTO.getProfLevel())) {
                violations.add(String.format("'%s' doesn't exist at system", masterDTO.getProfLevel()));
                break;
            }
        }
    }

    private void validateProfession(MasterDTO masterDTO, List<String> violations) {
        for (Profession value : Profession.values()) {
            if (!value.hasValue(masterDTO.getProfession())) {
                violations.add(String.format("'%s' doesn't exist at system", masterDTO.getProfession()));
                break;
            }
        }
    }
}


//        StringBuilder allLevels = new StringBuilder();
//        for (ProfLevel value : ProfLevel.values()) {
//
//            String level = value.getValue();
//            allLevels.append(level);}
//            if (!allLevels.toString().contains(masterDTO.getProfLevel())){
//                violations.add(String.format("'%s' doesn't exist at system", masterDTO.getProfLevel()));
//            }


//        StringBuilder allProfessions = new StringBuilder();
//        for (Profession value : Profession.values()) {
//            String level = value.getValue();
//            allProfessions.append(level);
//        }
//            if (!allProfessions.toString().contains(masterDTO.getProfession())){
//                violations.add(String.format("'%s' doesn't exist at system", masterDTO.getProfession()));
//            }
