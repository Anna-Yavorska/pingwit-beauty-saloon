package pingwit.beautysaloon.repository.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "procedures")
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "time")
    private BigDecimal time;

    public Procedure() {
    }

    public Procedure(Integer id, String name, String description, BigDecimal time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
