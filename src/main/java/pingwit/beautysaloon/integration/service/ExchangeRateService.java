package pingwit.beautysaloon.integration.service;

import pingwit.beautysaloon.integration.controller.dto.ExchangeRateDTO;

import java.util.List;
import java.util.Optional;


public interface ExchangeRateService {

    List<ExchangeRateDTO> findRate(String currencyCode);

}
