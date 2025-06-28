package QuanLyKhachSan.service;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import QuanLyKhachSan.repository.BookingRepository;
import QuanLyKhachSan.repository.CustomerRepository;
import QuanLyKhachSan.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid; // Thay javax.validation.Valid
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelManagement {
    private static final Logger logger = LoggerFactory.getLogger(HotelManagement.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PostConstruct
    public void initializeRooms() {
        if (roomRepository.count() == 0) {
            String[] roomTypes = {"Đơn", "Đôi"};
            for (int floor = 2; floor <= 6; floor++) {
                for (int i = 1; i <= 3; i++) {
                    int roomNumber = floor * 100 + i;
                    String type = roomTypes[(roomNumber % 2 == 0) ? 1 : 0];
                    Room room = new Room(roomNumber, type, true);
                    roomRepository.save(room);
                }
            }
            logger.info("Đã khởi tạo {} phòng.", roomRepository.count());
        }
    }

    public boolean addCustomer(@Valid Customer customer) {
        if (customer == null || customer.getIdCard() == null || customer.getIdCard().trim().isEmpty() ||
                !customer.getIdCard().matches("\\d{9,12}") || customer.getPhone() == null ||
                !customer.getPhone().matches("\\d{10,11}") || customerRepository.existsById(customer.getIdCard())) {
            logger.warn("Dữ liệu khách hàng không hợp lệ hoặc đã tồn tại: {}", customer);
            return false;
        }
        customerRepository.save(customer);
        logger.info("Đã thêm khách hàng với CMND/CCCD: {}", customer.getIdCard());
        return true;
    }

    public boolean deleteCustomer(String idCard) {
        if (idCard == null || idCard.trim().isEmpty()) {
            logger.warn("CMND/CCCD không hợp lệ để xóa.");
            return false;
        }
        if (customerRepository.existsById(idCard)) {
            bookingRepository.deleteAll(bookingRepository.findByCustomerIdCard(idCard));
            customerRepository.deleteById(idCard);
            roomRepository.findAll().forEach(room -> {
                if (bookingRepository.findAll().stream().noneMatch(b -> b.getRoom().getRoomNumber() == room.getRoomNumber())) {
                    room.setAvailable(true);
                    roomRepository.save(room);
                }
            });
            logger.info("Đã xóa khách hàng với CMND/CCCD: {}", idCard);
            return true;
        }
        logger.warn("Không tìm thấy khách hàng với CMND/CCCD: {}", idCard);
        return false;
    }

    public boolean updateCustomer(@Valid Customer updatedCustomer) {
        if (updatedCustomer == null || updatedCustomer.getIdCard() == null || updatedCustomer.getIdCard().trim().isEmpty() ||
                !updatedCustomer.getIdCard().matches("\\d{9,12}") || updatedCustomer.getPhone() == null ||
                !updatedCustomer.getPhone().matches("\\d{10,11}") || updatedCustomer.getName() == null ||
                updatedCustomer.getName().trim().isEmpty()) {
            logger.warn("Dữ liệu khách hàng không hợp lệ để cập nhật: {}", updatedCustomer);
            return false;
        }
        if (customerRepository.existsById(updatedCustomer.getIdCard())) {
            customerRepository.save(updatedCustomer);
            bookingRepository.findByCustomerIdCard(updatedCustomer.getIdCard()).forEach(booking -> {
                booking.setCustomer(updatedCustomer);
                bookingRepository.save(booking);
            });
            logger.info("Đã cập nhật khách hàng với CMND/CCCD: {}", updatedCustomer.getIdCard());
            return true;
        }
        logger.warn("Không tìm thấy khách hàng để cập nhật với CMND/CCCD: {}", updatedCustomer.getIdCard());
        return false;
    }

    public boolean addBooking(@Valid Booking booking) {
        if (booking == null || booking.getRoom() == null || booking.getCustomer() == null ||
                booking.getCheckIn() == null || booking.getCheckOut() == null ||
                !booking.getCheckOut().isAfter(booking.getCheckIn())) {
            logger.warn("Dữ liệu đặt phòng không hợp lệ: {}", booking);
            return false;
        }
        Room room = roomRepository.findById(booking.getRoom().getRoomNumber()).orElse(null);
        if (room == null || !room.isAvailable()) {
            logger.warn("Phòng số {} không có sẵn.", booking.getRoom() != null ? booking.getRoom().getRoomNumber() : "không xác định");
            return false;
        }
        if (!customerRepository.existsById(booking.getCustomer().getIdCard())) {
            if (!addCustomer(booking.getCustomer())) {
                logger.warn("Không thể thêm khách hàng để đặt phòng: {}", booking.getCustomer().getIdCard());
                return false;
            }
        }
        room.setAvailable(false);
        roomRepository.save(room);
        bookingRepository.save(booking);
        logger.info("Đã đặt phòng số {} thành công.", room.getRoomNumber());
        return true;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll().stream()
                .filter(room -> room != null)
                .collect(Collectors.toList());
    }

    public List<Customer> getActiveCustomers() {
        List<Customer> activeCustomers = bookingRepository.findAll().stream()
                .filter(booking -> booking != null && booking.getCustomer() != null && booking.getCustomer().getIdCard() != null &&
                        !booking.getCustomer().getIdCard().isEmpty() && booking.getCustomer().getIdCard().matches("\\d{9,12}"))
                .map(Booking::getCustomer)
                .filter(customer -> customer != null && customer.getIdCard() != null && !customer.getIdCard().isEmpty() &&
                        customer.getIdCard().matches("\\d{9,12}") && customer.getName() != null && !customer.getName().isEmpty() &&
                        customer.getPhone() != null && !customer.getPhone().isEmpty() && customer.getPhone().matches("\\d{10,11}"))
                .distinct()
                .collect(Collectors.toList());
        logger.debug("Tìm thấy {} khách hàng đang hoạt động.", activeCustomers.size());
        return activeCustomers;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking != null && booking.getCustomer() != null && booking.getRoom() != null)
                .collect(Collectors.toList());
    }
}