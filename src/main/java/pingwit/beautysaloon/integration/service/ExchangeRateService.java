package pingwit.beautysaloon.integration.service;

import pingwit.beautysaloon.integration.controller.dto.ExchangeRateDTO;

import java.util.List;


public interface ExchangeRateService {

    List<ExchangeRateDTO> findRate(String currencyCode);

}
