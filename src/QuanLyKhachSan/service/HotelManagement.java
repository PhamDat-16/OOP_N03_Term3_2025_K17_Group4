package QuanLyKhachSan.service;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelManagement {
    private static final Logger logger = LoggerFactory.getLogger(HotelManagement.class);
    private final List<Room> rooms = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public HotelManagement() {
        initializeRooms();
    }

    private void initializeRooms() {
        String[] roomTypes = {"Đơn", "Đôi"};
        for (int floor = 2; floor <= 6; floor++) {
            for (int i = 1; i <= 3; i++) {
                int roomNumber = floor * 100 + i;
                String type = roomTypes[(roomNumber % 2 == 0) ? 1 : 0];
                rooms.add(new Room(roomNumber, type, true));
            }
        }
        logger.info("Đã khởi tạo {} phòng", rooms.size());
    }

    public boolean addCustomer(Customer customer) {
        if (customer == null || customer.getIdCard() == null || customer.getIdCard().trim().isEmpty() ||
                !customer.getIdCard().matches("\\d{9,12}") || customer.getPhone() == null ||
                !customer.getPhone().matches("\\d{10,11}") ||
                customers.stream().anyMatch(c -> c != null && c.getIdCard().equals(customer.getIdCard()))) {
            logger.warn("Dữ liệu khách hàng không hợp lệ hoặc bị trùng: {}", customer);
            return false;
        }
        customers.add(customer);
        logger.info("Đã thêm khách hàng: {}", customer.getIdCard());
        return true;
    }

    public boolean deleteCustomer(String idCard) {
        if (idCard == null || idCard.trim().isEmpty()) {
            logger.warn("CMND/CCCD không hợp lệ!");
            return false;
        }
        boolean removed = customers.removeIf(c -> c != null && c.getIdCard().equals(idCard.trim()));
        if (removed) {
            bookings.removeIf(b -> b != null && b.getCustomer() != null && b.getCustomer().getIdCard().equals(idCard));
            rooms.forEach(r -> {
                if (r != null && bookings.stream().noneMatch(b -> b != null && b.getRoom() != null && b.getRoom().getRoomNumber() == r.getRoomNumber())) {
                    r.setAvailable(true);
                }
            });
            logger.info("Đã xóa khách hàng: {}", idCard);
            return true;
        }
        logger.warn("Không tìm thấy khách hàng để xóa: {}", idCard);
        return false;
    }

    public boolean updateCustomer(Customer updatedCustomer) {
        if (updatedCustomer == null || updatedCustomer.getIdCard() == null ||
                !updatedCustomer.getIdCard().matches("\\d{9,12}") ||
                updatedCustomer.getPhone() == null || !updatedCustomer.getPhone().matches("\\d{10,11}") ||
                updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            logger.warn("Dữ liệu khách hàng không hợp lệ để cập nhật: {}", updatedCustomer);
            return false;
        }
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i) != null && customers.get(i).getIdCard().equals(updatedCustomer.getIdCard())) {
                customers.set(i, new Customer(updatedCustomer.getName(), updatedCustomer.getIdCard(), updatedCustomer.getPhone()));
                bookings.forEach(b -> {
                    if (b != null && b.getCustomer() != null && b.getCustomer().getIdCard().equals(updatedCustomer.getIdCard())) {
                        b.setCustomer(new Customer(updatedCustomer.getName(), updatedCustomer.getIdCard(), updatedCustomer.getPhone()));
                    }
                });
                logger.info("Đã cập nhật khách hàng: {}", updatedCustomer.getIdCard());
                return true;
            }
        }
        logger.warn("Không tìm thấy khách hàng để cập nhật: {}", updatedCustomer.getIdCard());
        return false;
    }

    public boolean addBooking(Booking booking) {
        if (booking == null || booking.getRoom() == null || booking.getCustomer() == null ||
                booking.getCheckIn() == null || booking.getCheckOut() == null ||
                !booking.getCheckOut().isAfter(booking.getCheckIn())) {
            logger.warn("Dữ liệu đặt phòng không hợp lệ: {}", booking);
            return false;
        }
        Room room = rooms.stream()
                .filter(r -> r != null && r.getRoomNumber() == booking.getRoom().getRoomNumber())
                .findFirst()
                .orElse(null);
        if (room == null || !room.isAvailable()) {
            logger.warn("Phòng không có sẵn: {}", booking.getRoom().getRoomNumber());
            return false;
        }
        if (customers.stream().noneMatch(c -> c != null && c.getIdCard().equals(booking.getCustomer().getIdCard()))) {
            if (!addCustomer(new Customer(booking.getCustomer().getName(), booking.getCustomer().getIdCard(), booking.getCustomer().getPhone()))) {
                logger.warn("Không thể thêm khách hàng để đặt phòng: {}", booking.getCustomer().getIdCard());
                return false;
            }
        }
        room.setAvailable(false);
        bookings.add(new Booking(booking.getCustomer(), room, booking.getCheckIn(), booking.getCheckOut()));
        logger.info("Đã thêm đặt phòng cho phòng: {}", room.getRoomNumber());
        return true;
    }

    public List<Room> getRooms() {
        return rooms.stream()
                .filter(r -> r != null)
                .collect(Collectors.toList());
    }

    public List<Customer> getActiveCustomers() {
        List<Customer> activeCustomers = bookings.stream()
                .filter(b -> b != null && b.getCustomer() != null && b.getCustomer().getIdCard() != null && !b.getCustomer().getIdCard().isEmpty() && b.getCustomer().getIdCard().matches("\\d{9,12}"))
                .map(Booking::getCustomer)
                .filter(c -> c != null && c.getIdCard() != null && !c.getIdCard().isEmpty() && c.getIdCard().matches("\\d{9,12}") &&
                        c.getName() != null && !c.getName().isEmpty() &&
                        c.getPhone() != null && !c.getPhone().isEmpty() && c.getPhone().matches("\\d{10,11}"))
                .distinct()
                .collect(Collectors.toList());
        logger.debug("Đã tìm thấy {} khách hàng đang hoạt động", activeCustomers.size());
        return activeCustomers;
    }

    public List<Booking> getBookings() {
        return bookings.stream()
                .filter(b -> b != null && b.getCustomer() != null && b.getRoom() != null)
                .collect(Collectors.toList());
    }
}