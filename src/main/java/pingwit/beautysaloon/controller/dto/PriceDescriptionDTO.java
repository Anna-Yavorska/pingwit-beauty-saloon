package pingwit.beautysaloon.controller.dto;

import java.math.BigDecimal;

public class PriceDescriptionDTO {
    private String masterName;
    private String beautyProcedureName;
    private BigDecimal price;
    private String currency;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getBeautyProcedureName() {
        return beautyProcedureName;
    }

    public void setBeautyProcedureName(String beautyProcedureName) {
        this.beautyProcedureName = beautyProcedureName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
