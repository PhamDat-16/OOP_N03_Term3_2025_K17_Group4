package QuanLyKhachSan.service;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final List<Customer> customers = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public HotelService() {
        initializeRooms();
    }

    private void initializeRooms() {
        String[] types = {"Đơn", "Đôi"};
        for (int floor = 2; floor <= 6; floor++) {
            for (int room = 1; room <= 3; room++) {
                int roomNumber = floor * 100 + room;
                String type = types[(room - 1) % 2];
                rooms.add(new Room(roomNumber, type, true));
            }
        }
    }

    public boolean bookRoom(String name, String idCard, String phone, int roomNumber,
                            LocalDate checkIn, LocalDate checkOut) {
        Room room = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber && r.isAvailable())
                .findFirst().orElse(null);
        if (room == null) return false;

        Customer customer = new Customer(name, idCard, phone);
        customers.add(customer);
        Booking booking = new Booking(customer, room, checkIn, checkOut);
        bookings.add(booking);
        room.setAvailable(false);
        return true;
    }

    public List<Room> getAvailableRooms() {
        return rooms.stream().filter(Room::isAvailable).collect(Collectors.toList());
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public List<Customer> getActiveCustomers() {
        return bookings.stream().map(Booking::getCustomer).distinct().collect(Collectors.toList());
    }

    public boolean deleteCustomerByIdentifier(String identifier) {
        Customer customer = customers.stream()
                .filter(c -> c.getIdCard().equals(identifier) || c.getName().equalsIgnoreCase(identifier))
                .findFirst().orElse(null);

        if (customer == null) return false;

        List<Booking> toRemove = bookings.stream()
                .filter(b -> b.getCustomer().equals(customer)).collect(Collectors.toList());
        for (Booking booking : toRemove) {
            booking.getRoom().setAvailable(true);
            bookings.remove(booking);
        }

        customers.remove(customer);
        return true;
    }
}
