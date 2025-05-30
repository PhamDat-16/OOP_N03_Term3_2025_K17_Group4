package main.java.model;

public class Service {
    private static int nextId = 1;
    private int serviceId;
    private String serviceName;
    private double price;
    private int quantity;

    public Service(String serviceName, double price) {
        this(serviceName, price, 0);
    }
    public Service(String serviceName, double price , int quantity) {
        this.serviceId = nextId++;
        this.serviceName = serviceName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity;}
    public double getTotalPrice() {
        return price * quantity;
    }
    @Override
    public String toString() {
        return "Dich vu:  " + serviceName + 
        " | Don gia: " + price + 
        " VND | SL: "+ quantity + 
        " | Thanh: "+ getTotalPrice() + " VND";
    }
    
}
