package QuanLyKhachSan.controller;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import QuanLyKhachSan.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public String showDashboard() {
        return "index";
    }

    @GetMapping("/bookings/add")
    public String showAddBookingForm(Model model) {
        List<Room> availableRooms = hotelService.getAvailableRooms();
        model.addAttribute("rooms", availableRooms);
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

        // Kiểm tra đầu vào
        if (customerName.isEmpty() || customerIdCard.isEmpty() || customerPhone.isEmpty() ||
                !customerIdCard.matches("\\d{9,12}") || !customerPhone.matches("\\d{10,11}")) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng điền đầy đủ và đúng định dạng thông tin!");
            return "redirect:/bookings/add";
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);

            if (!checkOutDate.isAfter(checkInDate)) {
                redirectAttributes.addFlashAttribute("message", "Ngày check-out phải sau ngày check-in!");
                return "redirect:/bookings/add";
            }

            boolean success = hotelService.bookRoom(customerName, customerIdCard, customerPhone, roomNumber, checkInDate, checkOutDate);
            if (success) {
                redirectAttributes.addFlashAttribute("message", "Đặt phòng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Phòng không tồn tại hoặc đã được đặt!");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Định dạng ngày không hợp lệ!");
        }

        return "redirect:/bookings/add";
    }

    @GetMapping("/rooms")
    public String showRooms(Model model) {
        model.addAttribute("rooms", hotelService.getAllRooms());
        return "rooms";
    }

    @GetMapping("/customers/active")
    public String showActiveCustomers(Model model) {
        model.addAttribute("customers", hotelService.getActiveCustomers());
        return "active-customers";
    }

    @PostMapping("/customers/active/delete")
    public String deleteCustomer(@RequestParam("identifier") String identifier, RedirectAttributes redirectAttributes) {
        if (identifier.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng nhập tên hoặc CMND/CCCD!");
            return "redirect:/customers/active";
        }

        boolean deleted = hotelService.deleteCustomerByIdentifier(identifier);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Xóa khách hàng và đặt phòng liên quan thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy khách hàng!");
        }

        return "redirect:/customers/active";
    }

    @GetMapping("/active-rooms")
    public String showActiveRooms(Model model) {
        model.addAttribute("bookings", hotelService.getAllBookings());
        return "active-rooms";
    }
}
