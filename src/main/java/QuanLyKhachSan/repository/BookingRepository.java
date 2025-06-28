package QuanLyKhachSan.repository;

import QuanLyKhachSan.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerIdCard(String idCard);
}