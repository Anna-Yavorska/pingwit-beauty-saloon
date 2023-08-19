package pingwit.beautysaloon.integration.controller.dto;

public class ExchangeRateDTO {
    private String currency;
    private String nationalCurrency;
    private String buy;
    private String sale;

    public ExchangeRateDTO(String currency, String nationalCurrency, String buy, String sale) {
        this.currency = currency;
        this.nationalCurrency = nationalCurrency;
        this.buy = buy;
        this.sale = sale;
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
