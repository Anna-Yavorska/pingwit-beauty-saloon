package pingwit.beautysaloon.integration.service;

import pingwit.beautysaloon.integration.controller.dto.CurrencyDTO;

import java.util.List;


public interface CurrencyService {
    List<CurrencyDTO> findRate(String currencyCode);
}
