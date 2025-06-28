package QuanLyKhachSan.controller;

import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.service.CustomerManagementService;
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
    private final CustomerManagementService customerManagementService;

    public CustomerController(CustomerManagementService customerManagementService) {
        if (customerManagementService == null) {
            throw new IllegalArgumentException("CustomerManagementService cannot be null");
        }
        this.customerManagementService = customerManagementService;
    }

    @GetMapping("/customers/active")
    public String showActiveCustomers(Model model, @RequestParam(value = "identifier", required = false) String identifier) {
        try {
            List<Customer> customers = customerManagementService.getActiveCustomers().stream()
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
            logger.error("Lỗi khi tải danh sách khách hàng đang hoạt động: {}", e.getMessage(), e);
            model.addAttribute("message", "Lỗi khi tải danh sách khách hàng: " + e.getMessage());
            model.addAttribute("customers", new ArrayList<>());
        }
        return "active-customers";
    }

    @PostMapping("/customers/active/delete")
    public String deleteCustomer(@RequestParam("idCard") String idCard, RedirectAttributes redirectAttributes) {
        if (idCard == null || idCard.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "CMND/CCCD không hợp lệ!");
            return "redirect:/customers/active";
        }
        if (customerManagementService.deleteCustomer(idCard)) {
            redirectAttributes.addFlashAttribute("message", "Xóa khách hàng thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy khách hàng để xóa!");
        }
        return "redirect:/customers/active";
    }

    @PostMapping("/customers/active/update")
    public String updateCustomer(
            @RequestParam("idCard") String idCard,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            RedirectAttributes redirectAttributes) {
        if (idCard == null || idCard.trim().isEmpty() || name == null || name.trim().isEmpty() ||
                phone == null || !phone.matches("\\d{10,11}")) {
            redirectAttributes.addFlashAttribute("message", "Dữ liệu không hợp lệ (SĐT: 10-11 số)!");
            return "redirect:/customers/active";
        }
        Customer customer = new Customer(name.trim(), idCard.trim(), phone.trim());
        if (customerManagementService.updateCustomer(customer)) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật khách hàng thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy khách hàng để cập nhật!");
        }
        return "redirect:/customers/active";
    }
}