package test.java;
import model.*;
import java.time.LocalDate;
public class Booking {
    public static void main(String[] args) {
        Customer customer = new Customer("Tran Thi B", "987654321", "0987654321");
        Room room = new Room(102, "Standard");
        Booking booking = new Booking(customer, room, LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 12));

        System.out.println("Khách: " + customer.getName());
        System.out.println("Phòng số: " + room.isAvailable());
        System.out.println("Thời gian ở: " + booking.checkIn + " đến " + booking.checkOut);
    }
}

