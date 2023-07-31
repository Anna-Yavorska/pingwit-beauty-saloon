package pingwit.beautysaloon.validator;

import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.repositiry.model.ProfLevel;

public class MasterValidator {

    public void validate(MasterDTO masterDTO) {
        if (!ProfLevel.BASIC.hasValue(masterDTO.getProfLevel())) {
            //
        }
    }
}
