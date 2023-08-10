package pingwit.beautysaloon.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class ClientDTO {
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private Boolean isVip;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(id, clientDTO.id) && Objects.equals(name, clientDTO.name) && Objects.equals(surname, clientDTO.surname) && Objects.equals(phone, clientDTO.phone) && Objects.equals(email, clientDTO.email) && Objects.equals(isVip, clientDTO.isVip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phone, email, isVip);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isVip=" + isVip +
                '}';
    }
}
