package pingwit.beautysaloon.repositiry.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "services")
public class SaloonService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Master master;
    @Column(name = "date")
    private Date date;
    @ManyToOne
    private Procedure procedure;
    @Column(name = "price")
    private Double price;

    public SaloonService() {
    }

    public SaloonService(Integer id, String name, Client client, Master master, Date date, Procedure procedure, Double price) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.master = master;
        this.date = date;
        this.procedure = procedure;
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

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service{" +
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
