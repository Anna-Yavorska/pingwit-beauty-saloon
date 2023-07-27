package pingwit.beautysaloon.validator;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import pingwit.beautysaloon.controller.dto.ClientDTO;

@Component
public class ClientValidator {
    public void validateClient(ClientDTO clientDTO) {
        if (clientDTO.getName().contains("/d") && clientDTO.getSurname().contains("/d")) {
            throw new ValidationException("Client name and surname cannot contains numbers");
        }
    }
}
