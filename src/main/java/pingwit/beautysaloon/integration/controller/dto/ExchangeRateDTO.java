package pingwit.beautysaloon.integration.controller.dto;

public class ExchangeRateDTO {
    private String currencyCode;
    private String baseCurrencyCode;
    private String bankBuyAt;
    private String bankSellsAt;

    public ExchangeRateDTO(String currencyCode, String baseCurrencyCode, String bankBuyAt, String bankSellsAt) {
        this.currencyCode = currencyCode;
        this.baseCurrencyCode = baseCurrencyCode;
        this.bankBuyAt = bankBuyAt;
        this.bankSellsAt = bankSellsAt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getBankBuyAt() {
        return bankBuyAt;
    }

    public void setBankBuyAt(String bankBuyAt) {
        this.bankBuyAt = bankBuyAt;
    }

    public String getBankSellsAt() {
        return bankSellsAt;
    }

    public void setBankSellsAt(String bankSellsAt) {
        this.bankSellsAt = bankSellsAt;
    }
}
