// CustomerManagementServiceImpl.java
package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Booking;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.repository.BookingRepository;
import com.example.servingwebcontent.repository.CustomerRepository;
import com.example.servingwebcontent.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public CustomerManagementServiceImpl(CustomerRepository customerRepository,
                                         BookingRepository bookingRepository,
                                         RoomRepository roomRepository) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public String addCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getIdCard())) {
            return "Khách hàng đã tồn tại!";
        }
        customerRepository.save(customer);
        return "success";
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        Optional<Customer> existing = customerRepository.findById(customer.getIdCard());
        if (existing.isPresent()) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Customer> findCustomer(String idCard) {
        return customerRepository.findById(idCard);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public String deleteCustomer(String idCard) {
        try {
            List<Booking> bookings = bookingRepository.findByCustomerIdCard(idCard);
            for (Booking booking : bookings) {
                Room room = booking.getRoom();
                if (room != null) {
                    room.setAvailable(true);
                    roomRepository.save(room);
                }
                bookingRepository.delete(booking);
            }

            customerRepository.deleteById(idCard);
            return "success";
        } catch (Exception e) {
            return "Xóa thất bại: " + e.getMessage();
        }
    }
}