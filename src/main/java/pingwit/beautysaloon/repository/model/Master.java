package pingwit.beautysaloon.repository.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "masters")
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "prof_level")
    @Enumerated(EnumType.STRING)
    private ProfLevel profLevel;
    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;
    @ManyToMany
    @JoinTable(name = "master_procedure",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private List<Procedure> procedures;

    public Master() {
    }

    public Master(Integer id, String name, String surname, String phone, ProfLevel profLevel, Profession profession, List<Procedure> procedures) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.profLevel = profLevel;
        this.profession = profession;
        this.procedures = procedures;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ProfLevel getProfLevel() {
        return profLevel;
    }

    public void setProfLevel(ProfLevel profLevel) {
        this.profLevel = profLevel;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", profLevel=" + profLevel +
                ", profession=" + profession +
                ", procedures=" + procedures +
                '}';
    }
}
