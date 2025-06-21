package test;

import model.Customer;

public class CustomerTest {
    public static void TestCustomer() {
        HotelManagement hotel = new HotelManagement();


        Customer customer1 = new Customer("Tran Van Nhat", "06520059652005", "0969655965");
        if (hotel.addCustomer(customer1)) {
            System.out.println("Đã thêm khách hàng: " + customer1.getName());
        } else {
            System.out.println("Thêm khách hàng thất bại: CMND đã tồn tại");
        }


        System.out.println("\nDanh sách khách hàng:");
        for (Customer c : hotel.getCustomers()) {
            System.out.println("Tên: " + c.getName() + ", CMND: " + c.getIdCard() + ", SĐT: " + c.getPhone());
        }

        if (hotel.removeCustomer("Tran Van Nhat")) {
            System.out.println("\nĐã xóa khách hàng: Tran Van Nhat");
        } else {
            System.out.println("\nKhông tìm thấy khách hàng: Tran Van Nhat");
        }


        System.out.println("\nDanh sách khách hàng sau khi xóa:");
        if (hotel.getCustomers().isEmpty()) {
            System.out.println("Chưa có khách hàng nào.");
        } else {
            for (Customer c : hotel.getCustomers()) {
                System.out.println("Tên: " + c.getName() + ", CMND: " + c.getIdCard() + ", SĐT: " + c.getPhone());
            }
        }
    }
}