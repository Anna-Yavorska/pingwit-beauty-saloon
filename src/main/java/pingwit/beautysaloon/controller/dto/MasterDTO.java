package pingwit.beautysaloon.controller.dto;

import java.util.Collection;
import java.util.Objects;

public class MasterDTO {
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String profLevel;
    private String profession;
    private Collection<BeautyProcedureDTO> procedures;

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

    public String getProfLevel() {
        return profLevel;
    }

    public void setProfLevel(String profLevel) {
        this.profLevel = profLevel;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Collection<BeautyProcedureDTO> getProcedures() {
        return procedures;
    }

    public void setProcedures(Collection<BeautyProcedureDTO> procedures) {
        this.procedures = procedures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterDTO masterDTO = (MasterDTO) o;
        return Objects.equals(id, masterDTO.id) && Objects.equals(name, masterDTO.name) && Objects.equals(surname, masterDTO.surname) && Objects.equals(phone, masterDTO.phone) && Objects.equals(profLevel, masterDTO.profLevel) && Objects.equals(profession, masterDTO.profession) && Objects.equals(procedures, masterDTO.procedures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phone, profLevel, profession, procedures);
    }

    @Override
    public String toString() {
        return "MasterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", profLevel='" + profLevel + '\'' +
                ", profession='" + profession + '\'' +
                ", procedures=" + procedures +
                '}';
    }
}
