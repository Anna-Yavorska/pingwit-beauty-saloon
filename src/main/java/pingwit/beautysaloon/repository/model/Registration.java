package pingwit.beautysaloon.repository.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "services")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "procedures_id")
    private BeautyProcedure beautyProcedure;
    @Column(name = "price")
    private BigDecimal price;

    public Registration() {
    }

    public Registration(Integer id, String name, Client client, Master master, Date date, BeautyProcedure beautyProcedure, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.master = master;
        this.date = date;
        this.beautyProcedure = beautyProcedure;
        this.price = price;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BeautyProcedure getProcedure() {
        return beautyProcedure;
    }

    public void setProcedure(BeautyProcedure beautyProcedure) {
        this.beautyProcedure = beautyProcedure;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", client=" + client +
                ", master=" + master +
                ", date=" + date +
                ", procedure=" + beautyProcedure +
                ", price=" + price +
                '}';
    }
}
