package com.hotelbooking.controller;

import com.hotelbooking.model.Booking;
import com.hotelbooking.model.Customer;
import com.hotelbooking.model.Room;
import com.hotelbooking.service.HotelManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final HotelManagement hotelManagement;

    public BookingController(HotelManagement hotelManagement) {
        this.hotelManagement = hotelManagement;
    }

    @GetMapping("/bookings/add")
    public String showAddBookingForm(Model model) {
        try {
            List<Room> availableRooms = hotelManagement.getRooms().stream()
                    .filter(r -> r != null && r.isAvailable())
                    .collect(Collectors.toList());
            model.addAttribute("rooms", availableRooms);
            if (availableRooms.isEmpty()) {
                model.addAttribute("message", "Hiện không có phòng trống!");
            }
            logger.info("Đã tải {} phòng trống cho form đặt phòng", availableRooms.size());
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách phòng: {}", e.getMessage());
            model.addAttribute("message", "Lỗi khi tải danh sách phòng!");
            model.addAttribute("rooms", new ArrayList<>());
        }
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
        if (isInvalidInput(customerName, customerIdCard, customerPhone)) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng điền đầy đủ và đúng định dạng (CMND: 9-12 số, SĐT: 10-11 số)!");
            return "redirect:/bookings/add";
        }
        Room room = hotelManagement.getRooms().stream()
                .filter(r -> r != null && r.getRoomNumber() == roomNumber)
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
                redirectAttributes.addFlashAttribute("message", "Ngày trả phòng phải sau ngày nhận phòng!");
                return "redirect:/bookings/add";
            }
            Customer customer = new Customer(customerName.trim(), customerIdCard.trim(), customerPhone.trim());
            Booking booking = new Booking(customer, room, checkInDate, checkOutDate);
            if (hotelManagement.addBooking(booking)) {
                redirectAttributes.addFlashAttribute("message", "Đặt phòng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Đặt phòng thất bại do lỗi hệ thống!");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi phân tích ngày hoặc thêm đặt phòng: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Định dạng ngày không hợp lệ hoặc lỗi hệ thống!");
        }
        return "redirect:/bookings/add";
    }

    private boolean isInvalidInput(String name, String idCard, String phone) {
        return name == null || name.trim().isEmpty() ||
                idCard == null || idCard.trim().isEmpty() || !idCard.matches("\\d{9,12}") ||
                phone == null || phone.trim().isEmpty() || !phone.matches("\\d{10,11}");
    }
}