// CustomerManagementService.java
package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerManagementService {
    String addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Optional<Customer> findCustomer(String idCard);
    List<Customer> getAllCustomers();
    String deleteCustomer(String idCard);
}