package test.java;

public class Customer {
    private String name;
    private String idCard;
    private String phone;
    public Customer(String name, String idCard, String phone) {
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }
    public static void main(String[] args) {
        Customer customer = new Customer("Nguyen Van A", "123456789", "0912345678");

        System.out.println("Tên khách hàng: " + customer.Name());
        System.out.println("Số CMND: " + customer.idCard());
        System.out.println("Số điện thoại: " + customer.phone());
    }
}
