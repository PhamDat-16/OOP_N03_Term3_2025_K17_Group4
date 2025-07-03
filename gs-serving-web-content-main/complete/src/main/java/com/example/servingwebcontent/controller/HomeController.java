package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Booking;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.service.BookingManagementService;
import com.example.servingwebcontent.service.CustomerManagementService;
import com.example.servingwebcontent.service.RoomManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final BookingManagementService bookingManagementService;
    private final CustomerManagementService customerService;
    private final RoomManagementService roomService;

    @Autowired
    public HomeController(BookingManagementService bookingManagementService,
                          @Qualifier("customerManagementServiceImpl") CustomerManagementService customerService,
                          RoomManagementService roomService) {
        this.bookingManagementService = bookingManagementService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        logger.info("Showing home page");
        List<Booking> activeBookings = bookingManagementService.getAllBookings().stream()
                .filter(b -> b.getCheckOut() != null && b.getCheckOut().isAfter(LocalDate.now()))
                .toList();
        model.addAttribute("activeBookings", activeBookings);
        return "index";
    }

    @PostMapping("/bookings")
    public String addBooking(
            @RequestParam String customerIdCard,
            @RequestParam int roomNumber,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            RedirectAttributes redirectAttributes) {

        logger.info("Adding booking with customerIdCard: {}, roomNumber: {}, checkIn: {}, checkOut: {}",
                customerIdCard, roomNumber, checkIn, checkOut);

        if (customerIdCard == null || customerIdCard.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "CMND/CCCD không hợp lệ!");
            return "redirect:/";
        }

        Customer customer = customerService.findCustomer(customerIdCard).orElse(null);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("error", "Khách hàng không tồn tại!");
            return "redirect:/";
        }

        Room room = roomService.findRoom(roomNumber).orElse(null);
        if (room == null || !room.isAvailable()) {
            redirectAttributes.addFlashAttribute("error", "Phòng không tồn tại hoặc đã được đặt!");
            return "redirect:/";
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            if (!checkOutDate.isAfter(checkInDate)) {
                redirectAttributes.addFlashAttribute("error", "Ngày trả phòng phải sau ngày nhận phòng!");
                return "redirect:/";
            }

            Booking booking = new Booking(null, customer, room, checkInDate, checkOutDate);
            if (bookingManagementService.addBooking(booking)) {
                redirectAttributes.addFlashAttribute("message", "Thêm đặt phòng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Đặt phòng thất bại!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ngày không hợp lệ! Vui lòng dùng định dạng yyyy-MM-dd.");
        }

        return "redirect:/";
    }

    @GetMapping("/customer-list")
    public String showCustomers(Model model) {
        logger.info("Showing customer list");
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer-list";
    }

    @GetMapping("/bookings/new")
    public String showNewBookingForm(Model model) {
        logger.info("Showing new booking form");
        model.addAttribute("rooms", roomService.getAvailableRooms());
        return "add-booking";
    }

    @GetMapping("/room-list")
    public String showRooms(Model model) {
        logger.info("Showing room list");
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms-list";
    }

    @GetMapping("/active-bookings")
    public String showActiveBookings(Model model) {
        logger.info("Showing active bookings");
        List<Booking> activeBookings = bookingManagementService.getAllBookings().stream()
                .filter(b -> b.getCheckOut() != null && b.getCheckOut().isAfter(LocalDate.now()))
                .toList();
        model.addAttribute("activeBookings", activeBookings);
        return "index";
    }

    @GetMapping("/active-bookings/search")
    public String searchActiveBookings(@RequestParam(required = false) String idCard, Model model) {
        logger.info("Searching active bookings for idCard: {}", idCard);
        List<Booking> activeBookings = bookingManagementService.getAllBookings().stream()
                .filter(b -> b.getCheckOut() != null && b.getCheckOut().isAfter(LocalDate.now()))
                .filter(b -> idCard == null || idCard.trim().isEmpty() ||
                        (b.getCustomer() != null && b.getCustomer().getIdCard().equalsIgnoreCase(idCard)))
                .toList();
        model.addAttribute("activeBookings", activeBookings);
        if (activeBookings.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy đặt phòng đang hoạt động!");
        }
        return "index";
    }
}
