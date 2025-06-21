package QuanLyKhachSan.service;

import model.Booking;
import model.Customer;
import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import QuanLyKhachSan.repository.BookingRepository;
import QuanLyKhachSan.repository.CustomerRepository;
import QuanLyKhachSan.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoomRepository roomRepository;

    public boolean addBooking(Booking booking) {
        Room room = roomRepository.findById(booking.getRoom().getRoomNumber())
                .orElse(null);
        if (room == null || !room.isAvailable()) {
            return false;
        }
        Customer customer = customerRepository.findById(booking.getCustomer().getIdCard())
                .orElse(null);
        if (customer == null) {
            customerRepository.save(booking.getCustomer());
        }
        room.setAvailable(false);
        roomRepository.save(room);
        bookingRepository.save(booking);
        return true;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getActiveBookings() {
        // Giả sử active là booking có checkIn <= today <= checkOut
        LocalDate today = LocalDate.now();
        return bookingRepository.findAll().stream()
                .filter(b -> !b.getCheckIn().isAfter(today) && !b.getCheckOut().isBefore(today))
                .toList();
    }
}