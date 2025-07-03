package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Booking;
import com.example.servingwebcontent.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingManagementServiceImpl implements BookingManagementService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingManagementServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public boolean addBooking(Booking booking) {
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findBooking(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public String deleteBooking(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            bookingRepository.deleteById(id);
            return "success";
        } else {
            return "Không tìm thấy đặt phòng!";
        }
    }

    @Override
    public boolean updateBooking(Booking updatedBooking) {
        if (updatedBooking == null || updatedBooking.getId() == null) return false;

        Optional<Booking> existing = bookingRepository.findById(updatedBooking.getId());
        if (existing.isPresent()) {
            bookingRepository.save(updatedBooking);
            return true;
        }
        return false;
    }

    @Override
    public List<Booking> searchBookingsByCustomerId(String idCard) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getCustomer() != null && b.getCustomer().getIdCard().equals(idCard))
                .collect(Collectors.toList());
    }
}