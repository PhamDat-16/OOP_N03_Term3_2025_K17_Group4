// File: springboot/service/CustomerService.java
package springboot.service;

import model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final List<Customer> customerList = new ArrayList<>();

    public List<Customer> getAllCustomers() {
        return customerList;
    }

    public void addCustomer(Customer customer) {
        boolean exists = customerList.stream()
                .anyMatch(c -> c.getIdCard().equals(customer.getIdCard()));
        if (!exists) {
            customerList.add(customer);
        }
    }

    public void removeCustomer(String idCard) {
        customerList.removeIf(c -> c.getIdCard().equals(idCard));
    }

    public void findOrCreate(String name, String idCard, String phone) {

    }
}
;