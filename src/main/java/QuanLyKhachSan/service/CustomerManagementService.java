package QuanLyKhachSan.service;

import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.repository.CustomerRepository;
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

    public boolean addCustomer(@Valid Customer customer) {
        if (customer == null || customer.getIdCard() == null || customer.getIdCard().trim().isEmpty() ||
                !customer.getIdCard().matches("\\d{9,12}") || customer.getPhone() == null ||
                !customer.getPhone().matches("\\d{10,11}") || customerRepository.existsById(customer.getIdCard())) {
            logger.warn("Dữ liệu khách hàng không hợp lệ hoặc đã tồn tại: {}", customer);
            return false;
        }
        customerRepository.save(customer);
        logger.info("Đã thêm khách hàng với CMND/CCCD: {}", customer.getIdCard());
        return true;
    }

    public boolean deleteCustomer(String idCard) {
        if (idCard == null || idCard.trim().isEmpty()) {
            logger.warn("CMND/CCCD không hợp lệ để xóa.");
            return false;
        }
        if (customerRepository.existsById(idCard)) {
            customerRepository.deleteById(idCard);
            logger.info("Đã xóa khách hàng với CMND/CCCD: {}", idCard);
            return true;
        }
        logger.warn("Không tìm thấy khách hàng với CMND/CCCD: {}", idCard);
        return false;
    }

    public boolean updateCustomer(@Valid Customer updatedCustomer) {
        if (updatedCustomer == null || updatedCustomer.getIdCard() == null || updatedCustomer.getIdCard().trim().isEmpty() ||
                !updatedCustomer.getIdCard().matches("\\d{9,12}") || updatedCustomer.getPhone() == null ||
                !updatedCustomer.getPhone().matches("\\d{10,11}") || updatedCustomer.getName() == null ||
                updatedCustomer.getName().trim().isEmpty()) {
            logger.warn("Dữ liệu khách hàng không hợp lệ để cập nhật: {}", updatedCustomer);
            return false;
        }
        if (customerRepository.existsById(updatedCustomer.getIdCard())) {
            customerRepository.save(updatedCustomer);
            logger.info("Đã cập nhật khách hàng với CMND/CCCD: {}", updatedCustomer.getIdCard());
            return true;
        }
        logger.warn("Không tìm thấy khách hàng để cập nhật với CMND/CCCD: {}", updatedCustomer.getIdCard());
        return false;
    }

    public List<Customer> getActiveCustomers() {
        return customerRepository.findAll().stream()
                .filter(customer -> customer != null && customer.getIdCard() != null && !customer.getIdCard().isEmpty() &&
                        customer.getIdCard().matches("\\d{9,12}") && customer.getName() != null && !customer.getName().isEmpty() &&
                        customer.getPhone() != null && !customer.getPhone().isEmpty() && customer.getPhone().matches("\\d{10,11}"))
                .collect(Collectors.toList());
    }
}