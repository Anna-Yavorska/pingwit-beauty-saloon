package pingwit.beautysaloon.integration.controller.dto;

public class ExchangeRateDTO {
    private String ccy;
    private String bace_ccy;
    private String buy;
    private String sale;

    public ExchangeRateDTO(String ccy, String bace_ccy, String buy, String sale) {
        this.ccy = ccy;
        this.bace_ccy = bace_ccy;
        this.buy = buy;
        this.sale = sale;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBace_ccy() {
        return bace_ccy;
    }

    public void setBace_ccy(String bace_ccy) {
        this.bace_ccy = bace_ccy;
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
