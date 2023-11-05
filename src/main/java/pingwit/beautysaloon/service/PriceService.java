package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.PriceDescriptionDTO;

public interface PriceService {
    PriceDescriptionDTO calculatePrice(Integer masterId, Integer procedureId);
}
