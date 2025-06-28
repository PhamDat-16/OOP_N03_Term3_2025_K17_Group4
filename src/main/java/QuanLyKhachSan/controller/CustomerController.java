package com.hotelbooking.controller;

import com.hotelbooking.model.Customer;
import com.hotelbooking.service.HotelManagement;
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
    private final HotelManagement hotelManagement;

    public CustomerController(HotelManagement hotelManagement) {
        this.hotelManagement = hotelManagement;
    }

    @GetMapping("/customers/active")
    public String showActiveCustomers(Model model, @RequestParam(value = "identifier", required = false) String identifier) {
        try {
            List<Customer> customers = hotelManagement.getActiveCustomers().stream()
                    .filter(c -> c != null && c.getIdCard() != null && !c.getIdCard().isEmpty() && c.getIdCard().matches("\\d{9,12}"))
                    .collect(Collectors.toList());
            if (identifier != null && !identifier.trim().isEmpty()) {
                String searchTerm = identifier.trim().toLowerCase();
                customers = customers.stream()
                        .filter(c -> (c.getName() != null && !c.getName().isEmpty() && c.getName().toLowerCase().contains(searchTerm)) ||
                                c.getIdCard().contains(searchTerm))
                        .collect(Collectors.toList());
            }
            model.addAttribute("customers", customers);
            if (customers.isEmpty()) {
                model.addAttribute("message", "Không có khách hàng nào để hiển thị!");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách khách hàng đang hoạt động: {}", e.getMessage());
            model.addAttribute("message", "Lỗi khi tải danh sách khách hàng: " + e.getMessage());
            model.addAttribute("customers", new ArrayList<>());
        }
        return "active-customers";
    }

    @PostMapping("/customers/active/delete")
    public String deleteCustomer(@RequestParam("idCard") String idCard, RedirectAttributes redirectAttributes) {
        logger.info("Nhận yêu cầu xóa khách hàng với idCard: {}", idCard);
        if (idCard == null || idCard.trim().isEmpty() || !idCard.matches("\\d{9,12}")) {
            redirectAttributes.addFlashAttribute("message", "CMND/CCCD không hợp lệ! Phải là 9-12 số.");
            return "redirect:/customers/active";
        }
        try {
            if (hotelManagement.deleteCustomer(idCard.trim())) {
                redirectAttributes.addFlashAttribute("message", "Xóa khách hàng thành công!");
                logger.info("Đã xóa khách hàng với idCard {} thành công", idCard);
            } else {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy khách hàng để xóa!");
                logger.warn("Không tìm thấy khách hàng với idCard: {}", idCard);
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa khách hàng với idCard {}: {}", idCard, e.getMessage());
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
        logger.info("Nhận yêu cầu cập nhật khách hàng với idCard: {}, tên: {}, số điện thoại: {}", idCard, name, phone);
        if (idCard == null || idCard.trim().isEmpty() || !idCard.matches("\\d{9,12}")) {
            redirectAttributes.addFlashAttribute("message", "CMND/CCCD không hợp lệ! Phải là 9-12 số.");
            return "redirect:/customers/active";
        }
        if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Tên khách hàng không được để trống!");
            return "redirect:/customers/active";
        }
        if (phone == null || phone.trim().isEmpty() || !phone.matches("\\d{10,11}")) {
            redirectAttributes.addFlashAttribute("message", "Số điện thoại không hợp lệ! Phải là 10-11 số.");
            return "redirect:/customers/active";
        }
        try {
            Customer updatedCustomer = new Customer(name.trim(), idCard.trim(), phone.trim());
            if (hotelManagement.updateCustomer(updatedCustomer)) {
                redirectAttributes.addFlashAttribute("message", "Cập nhật khách hàng thành công!");
                logger.info("Đã cập nhật khách hàng với idCard {} thành công", idCard);
            } else {
                redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại: Khách hàng không tồn tại hoặc dữ liệu không hợp lệ!");
                logger.warn("Không thể cập nhật khách hàng với idCard: {}", idCard);
            }
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật khách hàng với idCard {}: {}", idCard, e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Lỗi hệ thống khi cập nhật khách hàng: " + e.getMessage());
        }
        return "redirect:/customers/active";
    }
}