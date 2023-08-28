package pingwit.beautysaloon.integration.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pingwit.beautysaloon.integration.controller.dto.ExchangeRateDTO;
import pingwit.beautysaloon.integration.model.ExchangeRate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private static final String EXCHANGE_RATE_PROVIDER_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";

    @Override
    public List<ExchangeRateDTO> findRate(String currencyCode) {
        RestTemplate restTemplate = new RestTemplate();

        ExchangeRate[] rates = restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, ExchangeRate[].class);
        assert rates != null;
        Stream<ExchangeRateDTO> rateDTOStream = Arrays.stream(rates)
                .map(rate -> new ExchangeRateDTO(rate.getCurrency(), rate.getNationalCurrency(), rate.getBuy(), rate.getSale()));
        if (currencyCode.isBlank()) {
            return rateDTOStream.toList();
        }
        return rateDTOStream
                .filter(rate -> currencyCode.equalsIgnoreCase(rate.getCurrency()))
                .toList();
    }
}
