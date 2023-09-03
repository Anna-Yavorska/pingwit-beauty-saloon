package pingwit.beautysaloon.integration.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import pingwit.beautysaloon.integration.controller.dto.CurrencyDTO;
import pingwit.beautysaloon.integration.model.Currency;

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
        Currency[] expected = new Currency[]{exchangeRateEUR(), exchangeRateUSD()};

        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, Currency[].class)).thenReturn(expected);

        //when
        List<CurrencyDTO> actual = target.findRate("");

        //then
        assertEquals(2, actual.size());
        assertThat(actual.get(0).getCurrency()).isEqualTo(expected[0].getForeignCurrency());
        assertThat(actual.get(0).getNationalCurrency()).isEqualTo(expected[0].getNationalCurrency());
        assertThat(actual.get(1).getCurrency()).isEqualTo(expected[1].getForeignCurrency());
        assertThat(actual.get(1).getNationalCurrency()).isEqualTo(expected[1].getNationalCurrency());
    }

    @Test
    @DisplayName("Should return Exchange rate for USD, if params was USD")
    void shouldFindExchangeRate_whenCodeWas() {
        //given
        Currency[] expected = new Currency[]{exchangeRateUSD()};

        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, Currency[].class)).thenReturn(expected);

        //when
        List<CurrencyDTO> actual = target.findRate(CURRENCY_CODE);

        //then
        assertEquals(1, actual.size());
        assertThat(actual.get(0).getCurrency()).isEqualTo(expected[0].getForeignCurrency());
        assertThat(actual.get(0).getNationalCurrency()).isEqualTo(expected[0].getNationalCurrency());
    }

    @Test
    @DisplayName("Should return empty List when params is invalid")
    void shouldBeEmptyList_whenParamsInvalid() {
        //given
        Currency[] rate = new Currency[]{};
        when(restTemplate.getForObject(EXCHANGE_RATE_PROVIDER_URL, Currency[].class)).thenReturn(rate);

        //when
        List<CurrencyDTO> actual = target.findRate("Code");

        //then
        assertThat(actual).isEmpty();
    }

    private Currency exchangeRateUSD() {
        Currency rate = new Currency();
        rate.setForeignCurrency("USD");
        rate.setNationalCurrency("UAH");
        rate.setBuy("27.5");
        rate.setSale("28.0");
        return rate;
    }

    private Currency exchangeRateEUR() {
        Currency rate = new Currency();
        rate.setForeignCurrency("EUR");
        rate.setNationalCurrency("UAH");
        rate.setBuy("33.0");
        rate.setSale("33.5");
        return rate;
    }
}