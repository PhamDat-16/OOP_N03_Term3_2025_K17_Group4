package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Booking;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.repository.BookingRepository;
import com.example.servingwebcontent.repository.CustomerRepository;
import com.example.servingwebcontent.repository.RoomRepository;
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
        if (booking == null || booking.getCustomer() == null || booking.getRoom() == null ||
                booking.getCheckIn() == null || booking.getCheckOut() == null ||
                !booking.getCheckOut().isAfter(booking.getCheckIn())) {
            logger.warn("Dữ liệu đặt phòng không hợp lệ: {}", booking);
            return false;
        }

        Room room = roomRepository.findById(booking.getRoom().getRoomNumber()).orElse(null);
        if (room == null || !room.isAvailable()) {
            logger.warn("Phòng {} không sẵn sàng.", booking.getRoom().getRoomNumber());
            return false;
        }

        if (!customerRepository.existsById(booking.getCustomer().getIdCard())) {
            customerRepository.save(booking.getCustomer());
        }

        room.setAvailable(false);
        roomRepository.save(room);
        bookingRepository.save(booking);
        logger.info("Đặt phòng thành công cho phòng {}", room.getRoomNumber());
        return true;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getCustomer() != null && b.getRoom() != null)
                .collect(Collectors.toList());
    }
}
