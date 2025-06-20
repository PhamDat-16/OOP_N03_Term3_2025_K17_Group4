package springboot.repository;

import model.Booking;
import model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.customer = :customer")
    void deleteByCustomer(Customer customer);

    List<Booking> findByCustomer(Customer customer);
}