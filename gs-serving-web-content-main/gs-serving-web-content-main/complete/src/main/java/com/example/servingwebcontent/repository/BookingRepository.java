package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerIdCard(String idCard);

    @Query("SELECT b FROM Booking b WHERE b.room.roomNumber = :roomNumber AND " +
            "(b.checkIn <= :checkOut AND b.checkOut >= :checkIn)")
    List<Booking> findConflictingBookings(@Param("roomNumber") int roomNumber,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);
}