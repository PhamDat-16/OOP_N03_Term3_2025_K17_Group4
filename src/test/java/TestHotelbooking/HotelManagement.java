package TestHotelbooking;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelManagement {
    private List<Customer> customers;
    private List<Booking> bookings;
    private List<Room> rooms;

    public HotelManagement() {
        this.customers = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.rooms = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        String[] types = {"Đơn", "Đôi"};
        for (int floor = 2; floor <= 6; floor++) {
            for (int room = 1; room <= 3; room++) {
                int roomNumber = floor * 100 + room;
                String type = types[(room - 1) % 2]; // Xen kẽ: Đơn cho phòng lẻ, Đôi cho phòng chẵn
                rooms.add(new Room(roomNumber, type, true));
                // In kiểm tra để debug
                System.out.println("Đã tạo phòng: " + roomNumber + ", Loại: " + type);
            }
        }
        System.out.println("Tổng số phòng được khởi tạo: " + rooms.size());
    }


    public boolean addCustomer(Customer customer) {
        if (customers.stream().anyMatch(c -> c.getIdCard().equals(customer.getIdCard()))) {
            return false; // CMND đã tồn tại
        }
        customers.add(customer);
        return true;
    }

    // Thêm đặt phòng
    public boolean addBooking(Booking booking) {
        Room room = rooms.stream()
                .filter(r -> r.getRoomNumber() == booking.getRoom().getRoomNumber())
                .findFirst()
                .orElse(null);
        if (room == null || !room.isAvailable()) {
            return false;
        }
        bookings.add(booking);
        room.setAvailable(false);
        return true;
    }


    public boolean removeCustomer(String identifier) {
        Customer customerToRemove = customers.stream()
                .filter(c -> c.getName().equals(identifier) || c.getIdCard().equals(identifier))
                .findFirst()
                .orElse(null);

        if (customerToRemove == null) {
            return false;
        }


        List<Booking> bookingsToRemove = bookings.stream()
                .filter(b -> b.getCustomer().equals(customerToRemove))
                .collect(Collectors.toList());

        for (Booking booking : bookingsToRemove) {
            booking.getRoom().setAvailable(true);
            bookings.remove(booking);
        }

        customers.remove(customerToRemove);
        return true;
    }


    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}