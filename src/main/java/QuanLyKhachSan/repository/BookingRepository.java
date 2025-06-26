package QuanLyKhachSan.repository;

import QuanLyKhachSan.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
