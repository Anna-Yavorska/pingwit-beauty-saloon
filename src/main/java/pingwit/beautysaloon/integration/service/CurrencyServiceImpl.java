package pingwit.beautysaloon.integration.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pingwit.beautysaloon.integration.controller.dto.CurrencyDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private static final String EXCHANGE_RATE_PROVIDER_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";

    @Override
    public List<CurrencyDTO> findRate(String currencyCode) {
        RestTemplate restTemplate = new RestTemplate();

        CurrencyDTO[] rates = restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, CurrencyDTO[].class);
        assert rates != null;
        Stream<CurrencyDTO> rateDTOStream = Arrays.stream(rates);

        if (currencyCode == null) {
            return rateDTOStream.toList();
        }
        return rateDTOStream
                .filter(rate -> currencyCode.equalsIgnoreCase(rate.getCurrency()))
                .toList();
    }
}
