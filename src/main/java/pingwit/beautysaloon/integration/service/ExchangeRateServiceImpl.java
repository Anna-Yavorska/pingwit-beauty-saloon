package pingwit.beautysaloon.integration.service;

import com.fasterxml.jackson.core.JsonToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pingwit.beautysaloon.integration.controller.dto.ExchangeRateDTO;
import pingwit.beautysaloon.integration.model.ExchangeRate;

import java.util.List;
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private static final String EXCHANGE_RATE_PROVIDER_URL="https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";

    @Override
    public ExchangeRateDTO findTwoRates() {
        RestTemplate restTemplate= new RestTemplate();

        ExchangeRate result = restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, ExchangeRate.class);

        return new ExchangeRateDTO(result.getCcy(), result.getBase_ccy(), result.getBuy(), result.getSale());
    }

    @Override
    public ExchangeRateDTO findRateByValuta(String ccy) {
        return null;
    }
}
