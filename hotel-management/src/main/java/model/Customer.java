package main.java.model;

public class Customer {
    private String name;
    private String idCard;
    private String phone;

    public Customer(String name, String idCard, String phone) {
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getPhone() {
        return phone;
    }
}