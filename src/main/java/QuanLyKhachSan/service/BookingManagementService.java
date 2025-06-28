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

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingManagementService {
    private static final Logger logger = LoggerFactory.getLogger(BookingManagementService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

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
            Customer customer = new Customer(booking.getCustomer().getName(), booking.getCustomer().getIdCard(), booking.getCustomer().getPhone());
            if (!new CustomerManagementService().addCustomer(customer)) {
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

    public List<Booking> getBookings() {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking != null && booking.getCustomer() != null && booking.getRoom() != null)
                .collect(Collectors.toList());
    }
}