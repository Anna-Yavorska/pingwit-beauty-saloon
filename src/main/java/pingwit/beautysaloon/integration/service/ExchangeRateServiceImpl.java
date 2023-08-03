package pingwit.beautysaloon.integration.service;

import com.fasterxml.jackson.core.JsonToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pingwit.beautysaloon.exception.NotFoundException;
import pingwit.beautysaloon.integration.controller.dto.ExchangeRateDTO;
import pingwit.beautysaloon.integration.model.ExchangeRate;
import pingwit.beautysaloon.integration.model.ExchangeRateWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private static final String EXCHANGE_RATE_PROVIDER_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";


    @Override
    public ExchangeRateDTO findRateByValuta(String currencyCode) {
        RestTemplate restTemplate = new RestTemplate();

        ExchangeRate[] rates = restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, ExchangeRate[].class);
        //ExchangeRateWrapper forObject = restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, ExchangeRateWrapper.class);

        Optional<ExchangeRate> rateOptional = Arrays.stream(rates)
                .filter(rate -> currencyCode.equalsIgnoreCase(rate.getCcy()))
                .findAny();
        return rateOptional.map(rate -> new ExchangeRateDTO(rate.getCcy(), rate.getBase_ccy(), rate.getBuy(), rate.getSale())).orElseThrow(() -> new NotFoundException("валюта не найдена!"));
    }
}
