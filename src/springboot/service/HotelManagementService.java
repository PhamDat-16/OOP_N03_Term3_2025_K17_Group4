package springboot.service;

import model.Booking;
import model.Customer;
import model.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelManagementService {
    private List<Customer> customers = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public boolean addCustomer(Customer customer) {
        if (customers.stream().anyMatch(c -> c.getIdCard().equals(customer.getIdCard()))) {
            return false;
        }
        customers.add(customer);
        return true;
    }

    public boolean addBooking(Booking booking) {
        Room room = rooms.stream().filter(r -> r.getRoomNumber() == booking.getRoom().getRoomNumber())
                .findFirst().orElse(null);
        if (room == null || !room.isAvailable()) {
            return false;
        }
        room.setAvailable(false);
        bookings.add(booking);
        return true;
    }

    public boolean addRoom(Room room) {
        if (rooms.stream().anyMatch(r -> r.getRoomNumber() == room.getRoomNumber())) {
            return false;
        }
        rooms.add(room);
        return true;
    }

    public List<Customer> getCustomers() { return customers; }
    public List<Room> getRooms() { return rooms; }
    public List<Booking> getBookings() { return bookings; }
}