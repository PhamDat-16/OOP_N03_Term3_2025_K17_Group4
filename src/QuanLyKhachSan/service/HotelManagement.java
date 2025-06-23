package QuanLyKhachSan.service;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelManagement {
    private List<Room> rooms = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public HotelManagement() {
        initializeRooms();
    }

    private void initializeRooms() {
        String[] roomTypes = {"Đơn", "Đôi"};
        int[] floors = {2, 3, 4, 5, 6};
        for (int floor : floors) {
            for (int i = 1; i <= 3; i++) {
                int roomNumber = floor * 100 + i;
                String type = roomTypes[(roomNumber % 2 == 0) ? 1 : 0];
                rooms.add(new Room(roomNumber, type, true));
            }
        }
    }

    public boolean addCustomer(Customer customer) {
        if (customer == null || customer.getIdCard() == null || !customer.getIdCard().matches("\\d{9,12}") ||
                customer.getPhone() == null || !customer.getPhone().matches("\\d{10,11}") ||
                customers.stream().anyMatch(c -> c.getIdCard().equals(customer.getIdCard()))) {
            return false;
        }
        customers.add(customer);
        return true;
    }

    public boolean deleteCustomer(String identifier) {
        Customer customer = customers.stream()
                .filter(c -> c.getIdCard().equals(identifier) || c.getName().equals(identifier))
                .findFirst()
                .orElse(null);
        if (customer == null) {
            return false;
        }
        List<Booking> relatedBookings = bookings.stream()
                .filter(b -> b.getCustomer().getIdCard().equals(customer.getIdCard()))
                .collect(Collectors.toList());
        for (Booking booking : relatedBookings) {
            Room room = rooms.stream()
                    .filter(r -> r.getRoomNumber() == booking.getRoom().getRoomNumber())
                    .findFirst()
                    .orElse(null);
            if (room != null) {
                room.setAvailable(true);
            }
        }
        bookings.removeIf(b -> b.getCustomer().getIdCard().equals(customer.getIdCard()));
        customers.removeIf(c -> c.getIdCard().equals(customer.getIdCard()));
        return true;
    }

    public boolean addBooking(Booking booking) {
        if (booking == null || booking.getRoom() == null || booking.getCustomer() == null ||
                booking.getCheckIn() == null || booking.getCheckOut() == null ||
                booking.getCheckOut().isBefore(booking.getCheckIn()) || booking.getCheckOut().isEqual(booking.getCheckIn())) {
            return false;
        }
        Room room = rooms.stream()
                .filter(r -> r.getRoomNumber() == booking.getRoom().getRoomNumber())
                .findFirst()
                .orElse(null);
        if (room == null || !room.isAvailable()) {
            return false;
        }
        Customer customer = customers.stream()
                .filter(c -> c.getIdCard().equals(booking.getCustomer().getIdCard()))
                .findFirst()
                .orElse(null);
        if (customer == null) {
            if (!addCustomer(booking.getCustomer())) {
                return false;
            }
        }
        room.setAvailable(false);
        bookings.add(booking);
        return true;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Customer> getActiveCustomers() {
        return bookings.stream()
                .map(Booking::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}