package pingwit.beautysaloon.validator;


import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.exception.BeautySalonValidationException;
import pingwit.beautysaloon.repository.ClientRepository;
import pingwit.beautysaloon.repository.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ClientValidator {
    private static final Pattern ONLY_LETTERS_PATTERN = Pattern.compile("^[a-zA-Z]*$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validateClient(ClientDTO clientDTO) {
        List<String> violations = new ArrayList<>();
        validateLetterField(clientDTO.getName(), "name", violations);
        validateLetterField(clientDTO.getSurname(), "surname", violations);
        validatePhone(clientDTO, violations);
        validateEmail(clientDTO, violations);

        if (!violations.isEmpty()) {
            throw new BeautySalonValidationException("Provided client is invalid!", violations);
        }
    }

    public void validateClientToUpdate(ClientDTO clientDTO) {
        List<String> violations = new ArrayList<>();
        validateLetterField(clientDTO.getName(), "name", violations);
        validateLetterField(clientDTO.getSurname(), "surname", violations);
        validatePhone(clientDTO, violations);
        validateEmailPattern(clientDTO, violations);

        if (!violations.isEmpty()) {
            throw new BeautySalonValidationException("Provided client is invalid!", violations);
        }
    }

    private void validateLetterField(String value, String fieldName, List<String> violations) {
        if (isBlank(value)) {
            violations.add(String.format("%s is blank", fieldName));
        }
        if (!ONLY_LETTERS_PATTERN.matcher(value).matches()) {
            violations.add(String.format("%s can contain only letters: '%s'", fieldName, value));
        }
    }

    private void validatePhone(ClientDTO clientDTO, List<String> violations) {
        if (isBlank(clientDTO.getPhone())) {
            violations.add("Phone is blank");
        }
        if (!PHONE_NUMBER_PATTERN.matcher(clientDTO.getPhone()).matches()) {
            violations.add(String.format("%s can contain only digits: '%s'", "phone", clientDTO.getPhone()));
        }
    }

    private void validateEmail(ClientDTO clientDTO, List<String> violations) {
        validateEmailPattern(clientDTO, violations);
        Client clientByEmail = clientRepository.findByEmail(clientDTO.getEmail());
        if (clientByEmail != null) {
            violations.add(String.format("email '%s' is already used in the system. Please choose a different one!", clientDTO.getEmail()));
        }
    }

    private void validateEmailPattern(ClientDTO clientDTO, List<String> violations) {
        if (!EMAIL_PATTERN.matcher(clientDTO.getEmail()).matches()) {
            violations.add(String.format("invalid email: '%s'", clientDTO.getEmail()));
        }
    }
}
