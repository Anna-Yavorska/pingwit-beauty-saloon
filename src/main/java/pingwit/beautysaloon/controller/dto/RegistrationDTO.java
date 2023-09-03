package pingwit.beautysaloon.controller.dto;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class RegistrationDTO {
    private Integer id;
    private String name;
    private ClientDTO client;
    private MasterDTO master;
    private Date date;
    private BeautyProcedureDTO procedure;
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public MasterDTO getMaster() {
        return master;
    }

    public void setMaster(MasterDTO master) {
        this.master = master;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BeautyProcedureDTO getProcedure() {
        return procedure;
    }

    public void setProcedure(BeautyProcedureDTO procedure) {
        this.procedure = procedure;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationDTO that = (RegistrationDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(client, that.client) && Objects.equals(master, that.master) && Objects.equals(date, that.date) && Objects.equals(procedure, that.procedure) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, client, master, date, procedure, price);
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", client=" + client +
                ", master=" + master +
                ", date=" + date +
                ", procedure=" + procedure +
                ", price=" + price +
                '}';
    }
}
