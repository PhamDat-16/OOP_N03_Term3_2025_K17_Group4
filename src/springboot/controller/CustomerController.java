package springboot.controller;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.service.HotelManagementService;

@Controller
public class CustomerController {
    @Autowired
    private HotelManagementService hotelService;

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        model.addAttribute("customers", hotelService.getCustomers());
        return "customers";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam String name, @RequestParam String idCard, @RequestParam String phone, Model model) {
        Customer customer = new Customer(name, idCard, phone);
        if (hotelService.addCustomer(customer)) {
            model.addAttribute("message", "Thêm khách hàng thành công!");
        } else {
            model.addAttribute("message", "CMND đã tồn tại!");
        }
        model.addAttribute("customers", hotelService.getCustomers());
        return "customers";
    }
}