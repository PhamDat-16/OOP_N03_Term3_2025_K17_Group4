package test.java;

import main.java.model.Booking;
import main.java.model.Customer;
import main.java.model.Room;

import java.time.LocalDate;

public class BookingTest {
    public static void main(String[] args) {
        Customer customer = new Customer("Tran Thi B", "987654321", "0987654321");
        Room room = new Room(102, "Standard");
        Booking booking = new Booking(customer, room, LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 12));

        System.out.println("Khách: " + customer.getName());
        System.out.println("Phòng số: " + room.getRoomNumber());
        System.out.println("Phòng có sẵn: " + room.isAvailable());
        System.out.println("Thời gian ở: " + booking.getCheckIn() + " đến " + booking.getCheckOut());
    }
}
