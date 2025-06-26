package QuanLyKhachSan.repository;

import QuanLyKhachSan.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByIdCard(String idCard);
}
