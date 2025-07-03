// CustomerController.java
package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.service.CustomerManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerManagementService customerService;

    @GetMapping
    public String showCustomerMenu(Model model) {
        return "customers";
    }

    @GetMapping("/add")
    public String showAddCustomerForm() {
        return "add-customer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        String result = customerService.addCustomer(customer);
        redirectAttributes.addFlashAttribute("message", result.equals("success") ? "Thêm khách hàng thành công!" : result);
        return "redirect:/customers";
    }

    @GetMapping("/list")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer-list";
    }

    @GetMapping("/search")
    public String searchForm() {
        return "search-customer";
    }

    @PostMapping("/search")
    public String searchCustomer(@RequestParam String idCard, Model model) {
        Customer customer = customerService.findCustomer(idCard).orElse(null);
        model.addAttribute("customer", customer);
        model.addAttribute("message", customer == null ? "Không tìm thấy khách hàng!" : null);
        return "search-customer";
    }

    @GetMapping("/edit")
    public String editCustomerForm(@RequestParam String idCard, Model model) {
        Customer customer = customerService.findCustomer(idCard).orElse(null);
        if (customer == null) {
            model.addAttribute("message", "Không tìm thấy khách hàng!");
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "edit-customer";
    }


    @PostMapping("/edit")
    public String editCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        boolean success = customerService.updateCustomer(customer);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật khách hàng thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại!");
        }
        return "redirect:/customers";
    }

    @GetMapping("/delete")
    public String deleteCustomerForm(@RequestParam String idCard, Model model) {
        Customer customer = customerService.findCustomer(idCard).orElse(null);
        if (customer == null) {
            model.addAttribute("message", "Không tìm thấy khách hàng!");
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "delete-customer";
    }

    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam String idCard, RedirectAttributes redirectAttributes) {
        String result = customerService.deleteCustomer(idCard);
        redirectAttributes.addFlashAttribute("message", result.equals("success") ? "Xóa thành công!" : result);
        return "redirect:/customers";
    }
}
