package QuanLyKhachSan.controller;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import QuanLyKhachSan.service.HotelManagement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
public class HotelController {
    private final HotelManagement hotelManagement;

    public HotelController(HotelManagement hotelManagement) {
        this.hotelManagement = hotelManagement;
    }

    @GetMapping("/")
    public String showDashboard() {
        return "index";
    }

    @GetMapping("/bookings/add")
    public String showAddBookingForm(Model model) {
        model.addAttribute("rooms", hotelManagement.getRooms().stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList()));
        return "add-booking";
    }

    @PostMapping("/bookings/add")
    public String addBooking(
            @RequestParam("customerName") String customerName,
            @RequestParam("customerIdCard") String customerIdCard,
            @RequestParam("customerPhone") String customerPhone,
            @RequestParam("roomNumber") int roomNumber,
            @RequestParam("checkIn") String checkIn,
            @RequestParam("checkOut") String checkOut,
            RedirectAttributes redirectAttributes) {
        if (customerName.isEmpty() || customerIdCard.isEmpty() || customerPhone.isEmpty() ||
                !customerIdCard.matches("\\d{9,12}") || !customerPhone.matches("\\d{10,11}")) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng điền đầy đủ và đúng định dạng thông tin!");
            return "redirect:/bookings/add";
        }
        Room room = hotelManagement.getRooms().stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
        if (room == null || !room.isAvailable()) {
            redirectAttributes.addFlashAttribute("message", "Phòng không tồn tại hoặc đã được đặt!");
            return "redirect:/bookings/add";
        }
        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
                redirectAttributes.addFlashAttribute("message", "Ngày check-out phải sau ngày check-in!");
                return "redirect:/bookings/add";
            }
            Customer customer = new Customer(customerName, customerIdCard, customerPhone);
            Booking booking = new Booking(customer, room, checkInDate, checkOutDate);
            if (hotelManagement.addBooking(booking)) {
                redirectAttributes.addFlashAttribute("message", "Đặt phòng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Đặt phòng thất bại!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Định dạng ngày không hợp lệ!");
        }
        return "redirect:/bookings/add";
    }

    @GetMapping("/rooms")
    public String showRooms(Model model) {
        model.addAttribute("rooms", hotelManagement.getRooms());
        return "rooms";
    }

    @GetMapping("/customers/active")
    public String showActiveCustomers(Model model) {
        model.addAttribute("customers", hotelManagement.getActiveCustomers());
        return "active-customers";
    }

    @PostMapping("/customers/active/delete")
    public String deleteCustomer(@RequestParam("identifier") String identifier, RedirectAttributes redirectAttributes) {
        if (identifier.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng nhập tên hoặc CMND/CCCD!");
            return "redirect:/customers/active";
        }
        if (hotelManagement.deleteCustomer(identifier)) {
            redirectAttributes.addFlashAttribute("message", "Xóa khách hàng và đặt phòng liên quan thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy khách hàng!");
        }
        return "redirect:/customers/active";
    }

    @GetMapping("/active-rooms")
    public String showActiveRooms(Model model) {
        model.addAttribute("bookings", hotelManagement.getBookings());
        return "active-rooms";
    }
}