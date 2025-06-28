package com.example.servingwebcontent.model;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    private String name;
    @Id
    private String idCard;
    private String phone;

    public Customer(String name, String idCard, String phone) {
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', idCard='" + idCard + "', phone='" + phone + "'}";
    }
}