package test.java;
import main.java.model.Customer;
public class CustomerTest {
    public static void main(String[] args) {
        Customer customer1 = new Customer(1, "Phạm Văn Đạt", "0326989362", "Phvdat220805@gmail.com" ,"Thái Bình" , "034205005976" ,"Việt Nam");
        Customer customer2 = new Customer(2, "Đặng Phương Anh", "0987654321" , "Dpanh161005@gmail.com" , "Thái Bình" , "034205005976" , "Việt Nam");
        System.out.println("== Kiem thu them khach hang ==");
        System.out.println(customer1);
        System.out.println(customer2);
    }
}
