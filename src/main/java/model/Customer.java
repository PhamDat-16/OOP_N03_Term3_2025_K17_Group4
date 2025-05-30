package main.java.model;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String customerIdCard;
    private String customerNationality;

    public Customer(int customerId, String customerName , String customerPhone , String customerEmail , String customerAddress , String customerIdCard , String customerNationality) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.customerIdCard = customerIdCard;
        this.customerNationality = customerNationality;
    }

    public int getId() { return customerId; }
    public String getName() { return customerName; }
    public String getPhone() { return customerPhone; }
    public String getEmail() {return customerEmail;}
    public String getAddress() {return customerAddress;}
    public String getIdCard() {return customerIdCard;}
    public String getNationality() {return customerNationality;}


    @Override
    public String toString() {
        return "ID: " + customerId + 
        ", Ten: " + customerName + 
        // ", SDT: " + customerPhone + 
        // "\n" + ", Email: "+ customerEmail + 
        // ", Dia chi nha: " + customerAddress +
        ", CCCD/CMND: " + customerIdCard +
        ", Quoc Tich: "+ customerNationality;
    }
}
