package pingwit.beautysaloon.integration.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import pingwit.beautysaloon.integration.controller.dto.CurrencyDTO;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurrencyServiceImplTest {
    private static final String EXCHANGE_RATE_PROVIDER_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
    private static final String CURRENCY_CODE = "USD";
    private final CurrencyServiceImpl target = new CurrencyServiceImpl();
    private final RestTemplate restTemplate = mock(RestTemplate.class);

    @Test
    @DisplayName("Should return List<ExchangeRateDTO> if params is empty")
    void shouldFindArrayOfRates_anyCodeWasNot() {
        //given
        CurrencyDTO[] expected = new CurrencyDTO[]{exchangeRateEUR(), exchangeRateUSD()};

        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, CurrencyDTO[].class)).thenReturn(expected);

        //when
        List<CurrencyDTO> actual = target.findRate(null);

        //then
        assertEquals(2, actual.size());
        List<CurrencyDTO> expectedList = Arrays.stream(expected).toList();
        assertThat(actual).usingRecursiveComparison().ignoringFields("buy", "sale").isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Should return Exchange rate for USD, if params was USD")
    void shouldFindExchangeRate_whenCodeWas() {
        //given
        CurrencyDTO[] expected = new CurrencyDTO[]{exchangeRateUSD()};

        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, CurrencyDTO[].class)).thenReturn(expected);

        //when
        List<CurrencyDTO> actual = target.findRate(CURRENCY_CODE);

        //then
        assertEquals(1, actual.size());
        List<CurrencyDTO> expectedList = Arrays.stream(expected).toList();
        assertThat(actual).usingRecursiveComparison().ignoringFields("buy", "sale").isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Should return empty List when params is invalid")
    void shouldBeEmptyList_whenParamsInvalid() {
        //given
        CurrencyDTO[] rate = new CurrencyDTO[]{};
        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, CurrencyDTO[].class)).thenReturn(rate);

        //when
        List<CurrencyDTO> actual = target.findRate("Code");

        //then
        assertThat(actual).isEmpty();
    }

    private CurrencyDTO exchangeRateUSD() {
        CurrencyDTO rate = new CurrencyDTO();
        rate.setCurrency("USD");
        rate.setNationalCurrency("UAH");
        return rate;
    }

    private CurrencyDTO exchangeRateEUR() {
        CurrencyDTO rate = new CurrencyDTO();
        rate.setCurrency("EUR");
        rate.setNationalCurrency("UAH");
        return rate;
    }
}