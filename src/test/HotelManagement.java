package test;

import model.Booking;
import model.Customer;
import model.Room;
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



        // Khởi tạo sẵn 15 phòng (tầng 2-6, mỗi tầng 3 phòng)
        private void initializeRooms () {
            String[] types = {"Đơn", "Đôi"};
            for (int floor = 2; floor <= 6; floor++) {
                for (int room = 1; room <= 3; room++) {
                    int roomNumber = floor * 100 + room;
                    String type = types[(roomNumber % 2)];
                    rooms.add(new Room(roomNumber, type));
                }
            }
        }

        // Thêm khách hàng mới
        public boolean addCustomer (Customer customer){
            if (customers.stream().anyMatch(c -> c.getIdCard().equals(customer.getIdCard()))) {
                return false; // CMND đã tồn tại
            }
            customers.add(customer);
            return true;
        }

        // Thêm đặt phòng
        public boolean addBooking (Booking booking){
            Room room = rooms.stream()
                    .filter(r -> r.getRoom() == booking.getRoom().getRoom())
                    .findFirst()
                    .orElse(null);
            if (room == null || !room.isAvailable()) {
                return false;
            }
            bookings.add(booking);
            room.setAvailable(false);
            return true;
        }

        // Xóa khách hàng dựa trên tên hoặc idCard
        public boolean removeCustomer (String identifier){
            Customer customerToRemove = customers.stream()
                    .filter(c -> c.getName().equals(identifier) || c.getIdCard().equals(identifier))
                    .findFirst()
                    .orElse(null);

            if (customerToRemove == null) {
                return false;
            }

            // Xóa tất cả các đặt phòng liên quan
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

        // Getter cho danh sách khách hàng, phòng, và đặt phòng
        public List<Customer> getCustomers () {
            return customers;
        }

        public List<Room> getRooms () {
            return rooms;
        }

        public List<Booking> getBookings () {
            return bookings;
        }
}