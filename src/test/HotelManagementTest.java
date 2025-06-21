package test;

import model.Booking;
import model.Customer;
import model.Room;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HotelManagementTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelManagement hotel = new HotelManagement();


        System.out.println("\nDanh Sách Phòng Sau Khởi Tạo:");
        if (hotel.getRooms().isEmpty()) {
            System.out.println("Chưa có phòng nào.");
        } else {
            for (Room room : hotel.getRooms()) {
                System.out.println("Phòng: " + room.getRoomNumber() +
                        ", Loại: " + (room.getType() != null ? room.getType() : "Không xác định") +
                        ", Trạng thái: " + (room.isAvailable() ? "Trống" : "Đã đặt"));
            }
        }

        System.out.println("\nNhập thông tin khách hàng:");
        System.out.print("Tên khách hàng: ");
        String name = scanner.nextLine().trim();
        System.out.print("CMND: ");
        String idCard = scanner.nextLine().trim();
        System.out.print("Số điện thoại: ");
        String phone = scanner.nextLine().trim();

        Customer customer1 = new Customer(name, idCard, phone);
        if (hotel.addCustomer(customer1)) {
            System.out.println("Đã thêm khách hàng: " + customer1.getName());
        } else {
            System.out.println("Thêm khách hàng thất bại: CMND đã tồn tại");
        }


        System.out.println("\nDanh sách khách hàng:");
        if (hotel.getCustomers().isEmpty()) {
            System.out.println("Chưa có khách hàng nào.");
        } else {
            for (Customer c : hotel.getCustomers()) {
                System.out.println("Tên: " + c.getName() + ", CMND: " + c.getIdCard() + ", SĐT: " + c.getPhone());
            }
        }

        // Đặt phòng
        System.out.println("\nĐặt phòng:");
        System.out.print("Nhập số phòng muốn đặt: ");
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Số phòng không hợp lệ!");
            scanner.close();
            return;
        }

        Room room = hotel.getRooms().stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);

        if (room != null) {
            LocalDate checkIn = null, checkOut = null;
            boolean validDate = false;

            while (!validDate) {
                try {
                    System.out.print("Nhập ngày check-in (YYYY-MM-DD): ");
                    checkIn = LocalDate.parse(scanner.nextLine().trim());
                    System.out.print("Nhập ngày check-out (YYYY-MM-DD): ");
                    checkOut = LocalDate.parse(scanner.nextLine().trim());

                    if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                        System.out.println("Ngày check-out phải sau ngày check-in!");
                    } else {
                        validDate = true;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng YYYY-MM-DD.");
                }
            }

            Booking booking = new Booking(customer1, room, checkIn, checkOut);
            if (hotel.addBooking(booking)) {
                System.out.println("Đã thêm đặt phòng cho khách: " + customer1.getName() + ", phòng: " + room.getRoomNumber());
            } else {
                System.out.println("Thêm đặt phòng thất bại: Phòng không tồn tại hoặc đã được đặt");
            }
        } else {
            System.out.println("Phòng " + roomNumber + " không tồn tại!");
        }


        System.out.println("\nDanh sách đặt phòng:");
        if (hotel.getBookings().isEmpty()) {
            System.out.println("Chưa có đặt phòng nào.");
        } else {
            for (Booking b : hotel.getBookings()) {
                System.out.println("Khách: " + b.getCustomer().getName() +
                        ", Phòng: " + b.getRoom().getRoomNumber() +
                        ", Loại: " + (b.getRoom().getType() != null ? b.getRoom().getType() : "Không xác định") +
                        ", Từ: " + b.getCheckIn() + " đến " + b.getCheckOut());
            }
        }


        System.out.println("\nTrạng thái phòng " + roomNumber + ": " + (room != null && room.isAvailable() ? "Trống" : "Đã đặt"));

        // Xóa khách hàng
        System.out.println("\nXóa khách hàng");
        System.out.print("Nhập tên hoặc CMND khách hàng muốn xóa: ");
        String identifier = scanner.nextLine().trim();
        if (hotel.removeCustomer(identifier)) {
            System.out.println("Đã xóa khách hàng: " + identifier);
        } else {
            System.out.println("Không tìm thấy khách hàng: " + identifier);
        }


        System.out.println("\nDanh sách khách hàng sau khi xóa:");
        if (hotel.getCustomers().isEmpty()) {
            System.out.println("Chưa có khách hàng nào.");
        } else {
            for (Customer c : hotel.getCustomers()) {
                System.out.println("Tên: " + c.getName() + ", CMND: " + c.getIdCard() + ", SĐT: " + c.getPhone());
            }
        }


        System.out.println("\nDanh sách đặt phòng sau khi xóa khách hàng:");
        if (hotel.getBookings().isEmpty()) {
            System.out.println("Chưa có đặt phòng nào.");
        } else {
            for (Booking b : hotel.getBookings()) {
                System.out.println("Khách: " + b.getCustomer().getName() +
                        ", Phòng: " + b.getRoom().getRoomNumber() +
                        ", Loại: " + (b.getRoom().getType() != null ? b.getRoom().getType() : "Không xác định") +
                        ", Từ: " + b.getCheckIn() + " đến " + b.getCheckOut());
            }
        }


        room = hotel.getRooms().stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
        System.out.println("\nTrạng thái phòng " + roomNumber + " sau khi xóa khách: " + (room != null && room.isAvailable() ? "Trống" : "Đã đặt"));

        scanner.close();
    }
}