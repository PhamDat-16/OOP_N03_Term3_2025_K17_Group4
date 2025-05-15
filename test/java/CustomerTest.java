package test.java;

import main.java.model.Customer;

public class CustomerTest {
    public static void main(String[] args) {
        Customer customer = new Customer("Nguyen Van A", "123456789", "0912345678");

        System.out.println("Ten khach haang: " + customer.getName());
        System.out.println("So CMND: " + customer.getIdCard());
        System.out.println("So dien thoai: " + customer.getPhone());
    }
}
