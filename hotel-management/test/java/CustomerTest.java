package test.java;
//import Model.Customer;
import main.java.model.Customer;

public class CustomerTest {
  
    public static void testCustomer() {
        Customer customer = new Customer("Nguyen Van A", "123456789", "0912345678");

        System.out.println("Tên khách hàng: " + customer.getName());
        //System.out.println("Số CMND: " + customer.idCard());
        //System.out.println("Số điện thoại: " + customer.phone());
    }
}
