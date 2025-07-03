package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingManagementService {
    boolean addBooking(Booking booking);

    List<Booking> getAllBookings();

    Optional<Booking> findBooking(Long id);

    String deleteBooking(Long id);

    boolean updateBooking(Booking updatedBooking);

    List<Booking> searchBookingsByCustomerId(String idCard);
}
