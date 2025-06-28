package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.service.CustomerManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerManagementService customerService;

    public CustomerController(CustomerManagementService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/active")
    public String showActiveCustomers(Model model, @RequestParam(value = "identifier", required = false) String identifier) {
        try {
            List<Customer> customers = customerService.getActiveCustomers();
            if (identifier != null && !identifier.trim().isEmpty()) {
                String searchTerm = identifier.trim().toLowerCase();
                customers = customers.stream()
                        .filter(c -> (c.getName() != null && c.getName().toLowerCase().contains(searchTerm)) ||
                                (c.getIdCard() != null && c.getIdCard().contains(searchTerm)))
                        .collect(Collectors.toList());
            }
            model.addAttribute("customers", customers);
            if (customers.isEmpty()) {
                model.addAttribute("message", "Không có khách hàng nào để hiển thị!");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách khách hàng: {}", e.getMessage());
            model.addAttribute("message", "Lỗi khi tải danh sách khách hàng: " + e.getMessage());
            model.addAttribute("customers", new ArrayList<>());
        }
        return "active-customers";
    }

    @PostMapping("/customers/active/delete")
    public String deleteCustomer(@RequestParam("idCard") String idCard, RedirectAttributes redirectAttributes) {
        logger.info("Yêu cầu xóa khách hàng với CMND/CCCD: {}", idCard);
        try {
            boolean success = customerService.deleteCustomer(idCard.trim());
            if (success) {
                redirectAttributes.addFlashAttribute("message", "Xóa khách hàng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy khách hàng để xóa hoặc CMND/CCCD không hợp lệ!");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa khách hàng: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa khách hàng: " + e.getMessage());
        }
        return "redirect:/customers/active";
    }

    @PostMapping("/customers/active/update")
    public String updateCustomer(
            @RequestParam("idCard") String idCard,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            RedirectAttributes redirectAttributes) {
        logger.info("Yêu cầu cập nhật khách hàng - ID: {}, Tên: {}, SĐT: {}", idCard, name, phone);
        try {
            Customer customer = new Customer(name.trim(), idCard.trim(), phone.trim());
            boolean success = customerService.updateCustomer(customer);
            if (success) {
                redirectAttributes.addFlashAttribute("message", "Cập nhật khách hàng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại: Dữ liệu không hợp lệ hoặc khách hàng không tồn tại!");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật khách hàng: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Lỗi khi cập nhật khách hàng: " + e.getMessage());
        }
        return "redirect:/customers/active";
    }
}
