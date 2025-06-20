package test;



import model.Booking;
import model.Customer;
import model.Room;

import java.time.LocalDate;

public class HotelManagementTest {
    public static void main(String[] args) {
        HotelManagement hotel = new HotelManagement();

        // Nhật - Khách Hàng
        System.out.println("Nhập thông tin khách hàng: ");
        Customer customer1 = new Customer("Tran Van Nhat", "06520059652005", "0969655965");
        if (hotel.addCustomer(customer1)) {
            System.out.println("Đã thêm khách hàng: " + customer1.getName());
        } else {
            System.out.println("Thêm khách hàng thất bại: CMND đã tồn tại");
        }

        // Nhật - In danh sách khách
        System.out.println("\nDanh sách khách hàng:");
        for (Customer c : hotel.getCustomers()) {
            System.out.println("Tên: " + c.getName() + ", CMND: " + c.getIdCard() + ", SĐT: " + c.getPhone());
        }

        // Đạt - Quản lí phòng
        System.out.println("\nDanh Sách Phòng");
        for (Room room : hotel.getRooms()) {
            System.out.println("Phòng: " + room.getRoomNumber() +
                    ", Loại: " + room.getType() +
                    ", Trạng thái: " + (room.isAvailable() ? "Trống" : "Đã đặt"));
        }

        // Bảo - Đặt Phòng
        System.out.println("\nĐặt phòng");
        Room room201 = hotel.getRooms().stream()
                .filter(r -> r.getRoomNumber() == 201)
                .findFirst()
                .orElse(null);

        if (room201 != null) {
            Booking booking = new Booking(customer1, room201, LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 12));
            if (hotel.addBooking(booking)) {
                System.out.println("Đã thêm đặt phòng cho khách: " + customer1.getName() + ", phòng: " + room201.getRoomNumber());
            } else {
                System.out.println("Thêm đặt phòng thất bại: Phòng không tồn tại hoặc đã được đặt");
            }
        }

        // In danh sách đặt phòng
        System.out.println("\nDanh sách đặt phòng:");
        for (Booking b : hotel.getBookings()) {
            System.out.println("Khách: " + b.getCustomer().getName() +
                    ", Phòng: " + b.getRoom().getRoomNumber() +
                    ", Loại: " + b.getRoom().getType() +
                    ", Từ: " + b.getCheckIn() + " đến " + b.getCheckOut());
        }

        // Kiểm tra trạng thái phòng 201
        System.out.println("\nTrạng thái phòng 201: " + (room201.isAvailable() ? "Trống" : "Đã đặt"));

        // Nhật - Xóa Khách Hàng
        System.out.println("\n=== Xóa khách hàng ===");
        if (hotel.removeCustomer("Tran Van Nhat")) {
            System.out.println("Đã xóa khách hàng: Tran Van Nhat");
        } else {
            System.out.println("Không tìm thấy khách hàng: Tran Van Nhat");
        }

        // In lại danh sách khách hàng sau khi xóa
        System.out.println("\nDanh sách khách hàng sau khi xóa:");
        if (hotel.getCustomers().isEmpty()) {
            System.out.println("Chưa có khách hàng nào.");
        } else {
            for (Customer c : hotel.getCustomers()) {
                System.out.println("Tên: " + c.getName() + ", CMND: " + c.getIdCard() + ", SĐT: " + c.getPhone());
            }
        }

        // In lại danh sách đặt phòng sau khi xóa khách hàng
        System.out.println("\nDanh sách đặt phòng sau khi xóa khách hàng:");
        if (hotel.getBookings().isEmpty()) {
            System.out.println("Chưa có đặt phòng nào.");
        } else {
            for (Booking b : hotel.getBookings()) {
                System.out.println("Khách: " + b.getCustomer().getName() +
                        ", Phòng: " + b.getRoom().getRoomNumber() +
                        ", Loại: " + b.getRoom().getType() +
                        ", Từ: " + b.getCheckIn() + " đến " + b.getCheckOut());
            }
        }

        // Kiểm tra trạng thái phòng 201 sau khi xóa khách
        System.out.println("\nTrạng thái phòng 201 sau khi xóa khách: " + (room201.isAvailable() ? "Trống" : "Đã đặt"));
    }
}
