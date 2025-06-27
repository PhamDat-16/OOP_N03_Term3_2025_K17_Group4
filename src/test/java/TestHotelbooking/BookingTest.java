package TestHotelbooking;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import java.time.LocalDate;

public class BookingTest {
    public static void TestBooking() {
        HotelManagement hotel = new HotelManagement();

        // Thêm khách hàng
        Customer customer1 = new Customer("Tran Van Nhat", "0303969655", "0345987123");
        if (hotel.addCustomer(customer1)) {
            System.out.println("Đã thêm khách hàng: " + customer1.getName());
        } else {
            System.out.println("Thêm khách hàng thất bại: CMND đã tồn tại");
        }

        // Thêm đặt phòng
        Room room = hotel.getRooms().stream()
                .filter(r -> r.getRoomNumber() == 201)
                .findFirst()
                .orElse(null);

        if (room != null) {
            Booking booking = new Booking(customer1, room, LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 12));
            if (hotel.addBooking(booking)) {
                System.out.println("Đã thêm đặt phòng cho khách: " + customer1.getName() + ", phòng: " + room.getRoomNumber());
            } else {
                System.out.println("Thêm đặt phòng thất bại: Phòng không tồn tại hoặc đã được đặt");
            }
        }

        // In danh sách đặt phòng
        System.out.println("\nDanh sách đặt phòng:");
        for (Booking b : hotel.getBookings()) {
            if (b.getCustomer() != null) {
                System.out.println("Khách: " + b.getCustomer().getName() +
                        ", Phòng: " + b.getRoom().getRoomNumber() +
                        ", Loại: " + b.getRoom().getType() +
                        ", Từ: " + b.getCheckIn() + " đến " + b.getCheckOut());
            }
        }

        // Kiểm tra trạng thái phòng
        System.out.println("\nTrạng thái phòng 201: " +
                (room != null && !room.isAvailable() ? "Đã đặt" : "Trống"));
    }
}