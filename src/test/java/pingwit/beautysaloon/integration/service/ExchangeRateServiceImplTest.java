package pingwit.beautysaloon.integration.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import pingwit.beautysaloon.integration.controller.dto.ExchangeRateDTO;
import pingwit.beautysaloon.integration.model.ExchangeRate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExchangeRateServiceImplTest {
    private static final String EXCHANGE_RATE_PROVIDER_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
    private static final String CURRENCY_CODE = "USD";
    private final ExchangeRateServiceImpl target = new ExchangeRateServiceImpl();
    private final RestTemplate restTemplate = mock(RestTemplate.class);

    @Test
    @DisplayName("Should return List<ExchangeRateDTO> if params is empty")
    void shouldFindArrayOfRates_anyCodeWasNot() {
        //given
        ExchangeRate[] expected = new ExchangeRate[]{exchangeRateEUR(), exchangeRateUSD()};

        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, ExchangeRate[].class)).thenReturn(expected);

        //when
        List<ExchangeRateDTO> actual = target.findRate("");

        //then
        assertEquals(2, actual.size());
        assertThat(actual.get(0).getCurrency()).isEqualTo(expected[0].getCurrency());
        assertThat(actual.get(0).getNationalCurrency()).isEqualTo(expected[0].getNationalCurrency());
        assertThat(actual.get(1).getCurrency()).isEqualTo(expected[1].getCurrency());
        assertThat(actual.get(1).getNationalCurrency()).isEqualTo(expected[1].getNationalCurrency());
    }

    @Test
    @DisplayName("Should return Exchange rate for USD, if params was USD")
    void shouldFindExchangeRate_whenCodeWas() {
        //given
        ExchangeRate[] expected = new ExchangeRate[]{exchangeRateUSD()};

        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, ExchangeRate[].class)).thenReturn(expected);

        //when
        List<ExchangeRateDTO> actual = target.findRate(CURRENCY_CODE);

        //then
        assertEquals(1, actual.size());
        assertThat(actual.get(0).getCurrency()).isEqualTo(expected[0].getCurrency());
        assertThat(actual.get(0).getNationalCurrency()).isEqualTo(expected[0].getNationalCurrency());
    }

    @Test
    @DisplayName("Should return empty List when params is invalid")
    void shouldBeEmptyList_whenParamsInvalid() {
        //given
        ExchangeRate[] rate = new ExchangeRate[]{};
        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, ExchangeRate[].class)).thenReturn(rate);

        //when
        List<ExchangeRateDTO> actual = target.findRate("Code");

        //then
        assertThat(actual).isEmpty();
    }

    private ExchangeRate exchangeRateUSD() {
        ExchangeRate rate = new ExchangeRate();
        rate.setCurrency("USD");
        rate.setNationalCurrency("UAH");
        rate.setBuy("27.5");
        rate.setSale("28.0");
        return rate;
    }

    private ExchangeRate exchangeRateEUR() {
        ExchangeRate rate = new ExchangeRate();
        rate.setCurrency("EUR");
        rate.setNationalCurrency("UAH");
        rate.setBuy("33.0");
        rate.setSale("33.5");
        return rate;
    }
}