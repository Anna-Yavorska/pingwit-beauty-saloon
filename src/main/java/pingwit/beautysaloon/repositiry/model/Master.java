package pingwit.beautysaloon.repositiry.model;

import jakarta.persistence.*;

import java.util.Set;

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
    private String profession;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "master_procedure",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<Procedure> procedures;
    public Master() {
    }

    public Master(Integer id, String name, String surname, String phone, ProfLevel profLevel, String profession) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.profLevel = profLevel;
        this.profession = profession;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Set<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(Set<Procedure> procedures) {
        this.procedures = procedures;
    }

    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", profLevel='" + profLevel + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }
}
