package pingwit.beautysaloon.integration.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyDTO {
    @JsonProperty("ccy")
    private String currency;
    @JsonProperty("base_ccy")
    private String nationalCurrency;
    private String buy;
    private String sale;

    public CurrencyDTO() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNationalCurrency() {
        return nationalCurrency;
    }

    public void setNationalCurrency(String nationalCurrency) {
        this.nationalCurrency = nationalCurrency;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }
}
