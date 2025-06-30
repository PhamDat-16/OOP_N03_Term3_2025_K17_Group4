package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Booking;
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
public class CustomerManagementService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerManagementService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;


    public boolean addCustomer(@Valid Customer customer) {
        if (customer == null || customer.getIdCard() == null || customer.getIdCard().trim().isEmpty() ||
                !customer.getIdCard().matches("\\d{9,12}") || customer.getPhone() == null ||
                !customer.getPhone().matches("\\d{10,11}") || customerRepository.existsById(customer.getIdCard())) {
            logger.warn("Dữ liệu khách hàng không hợp lệ hoặc đã tồn tại: {}", customer);
            return false;
        }
        customerRepository.save(customer);
        logger.info("Đã thêm khách hàng: {}", customer.getIdCard());
        return true;
    }

    public boolean updateCustomer(@Valid Customer customer) {
        if (customer == null || customer.getIdCard() == null || customer.getIdCard().trim().isEmpty() ||
                !customer.getIdCard().matches("\\d{9,12}") || customer.getPhone() == null ||
                !customer.getPhone().matches("\\d{10,11}") || customer.getName() == null || customer.getName().trim().isEmpty()) {
            logger.warn("Dữ liệu khách hàng không hợp lệ để cập nhật: {}", customer);
            return false;
        }
        if (customerRepository.existsById(customer.getIdCard())) {
            customerRepository.save(customer);
            // cập nhật cả thông tin khách trong các booking
            bookingRepository.findByCustomerIdCard(customer.getIdCard()).forEach(booking -> {
                booking.setCustomer(customer);
                bookingRepository.save(booking);
            });
            logger.info("Đã cập nhật khách hàng: {}", customer.getIdCard());
            return true;
        }
        logger.warn("Không tìm thấy khách hàng để cập nhật: {}", customer.getIdCard());
        return false;
    }

    public boolean deleteCustomer(String idCard) {
        if (idCard == null || idCard.trim().isEmpty()) {
            logger.warn("CMND/CCCD không hợp lệ.");
            return false;
        }
        if (customerRepository.existsById(idCard)) {
            // Xóa tất cả booking liên quan
            List<Booking> bookings = bookingRepository.findByCustomerIdCard(idCard);

            // trả phòng cho các booking này
            bookings.forEach(booking -> {
                if (booking.getRoom() != null) {
                    Room room = booking.getRoom();
                    room.setAvailable(true);
                    roomRepository.save(room);
                    logger.info("Đã trả phòng {}", room.getRoomNumber());
                }
            });

            bookingRepository.deleteAll(bookings);
            customerRepository.deleteById(idCard);
            logger.info("Đã xóa khách hàng: {}", idCard);
            return true;
        }
        logger.warn("Không tìm thấy khách hàng để xóa: {}", idCard);
        return false;
    }


    public List<Customer> getActiveCustomers() {
        return bookingRepository.findAll().stream()
                .map(Booking::getCustomer)
                .filter(c -> c != null && c.getIdCard() != null && !c.getIdCard().isEmpty() &&
                        c.getIdCard().matches("\\d{9,12}") &&
                        c.getName() != null && !c.getName().isEmpty() &&
                        c.getPhone() != null && c.getPhone().matches("\\d{10,11}"))
                .distinct()
                .collect(Collectors.toList());
    }
}
