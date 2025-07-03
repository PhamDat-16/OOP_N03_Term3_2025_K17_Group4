package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Booking;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.service.BookingManagementService;
import com.example.servingwebcontent.service.CustomerManagementService;
import com.example.servingwebcontent.service.RoomManagementService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingManagementService bookingService;
    private final CustomerManagementService customerService;
    private final RoomManagementService roomService;

    public BookingController(BookingManagementService bookingService,
                             CustomerManagementService customerService,
                             @Qualifier("roomManagementServiceImpl") RoomManagementService roomService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    // Menu chính
    @GetMapping
    public String showBookingMenu() {
        return "bookings";
    }

    // Giao diện thêm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("rooms", roomService.getAvailableRooms());
        return "add-booking";
    }

    // Xử lý thêm
    @PostMapping("/add")
    public String addBooking(@RequestParam String customerIdCard,
                             @RequestParam int roomNumber,
                             @RequestParam String checkIn,
                             @RequestParam String checkOut,
                             RedirectAttributes redirectAttributes) {
        Customer customer = customerService.findCustomer(customerIdCard).orElse(null);
        Room room = roomService.findRoom(roomNumber).orElse(null);

        if (customer == null) {
            redirectAttributes.addFlashAttribute("message", "Khách hàng không tồn tại!");
            return "redirect:/bookings/add";
        }

        if (room == null || !room.isAvailable()) {
            redirectAttributes.addFlashAttribute("message", "Phòng không tồn tại hoặc đã được đặt!");
            return "redirect:/bookings/add";
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            if (!checkOutDate.isAfter(checkInDate)) {
                redirectAttributes.addFlashAttribute("message", "Ngày trả phải sau ngày nhận!");
                return "redirect:/bookings/add";
            }

            Booking booking = new Booking(null, customer, room, checkInDate, checkOutDate);
            boolean success = bookingService.addBooking(booking);
            redirectAttributes.addFlashAttribute("message", success ? "Đặt phòng thành công!" : "Đặt phòng thất bại!");

        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("message", "Ngày không hợp lệ!");
        }

        return "redirect:/bookings/list";
    }

    // Danh sách
    @GetMapping("/list")
    public String showBookingList(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookings-list";
    }

    // Giao diện xóa
    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam Long id, Model model) {
        Booking booking = bookingService.findBooking(id).orElse(null);
        if (booking == null) {
            model.addAttribute("message", "Không tìm thấy đặt phòng!");
            return "redirect:/bookings/list";
        }
        model.addAttribute("booking", booking);
        return "delete-booking";
    }

    // Xử lý xóa
    @PostMapping("/delete")
    public String deleteBooking(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        String result = bookingService.deleteBooking(id);
        redirectAttributes.addFlashAttribute("message",
                result.equals("success") ? "Xóa đặt phòng thành công!" : result);
        return "redirect:/bookings/list";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model) {
        Booking booking = bookingService.findBooking(id).orElse(null);
        if (booking == null) {
            model.addAttribute("message", "Không tìm thấy đặt phòng!");
            return "redirect:/bookings/list";
        }
        model.addAttribute("booking", booking);
        model.addAttribute("rooms", roomService.getAllRooms());
        return "edit-booking";
    }

    @PostMapping("/edit")
    public String editBooking(@RequestParam Long id,
                              @RequestParam int roomNumber,
                              @RequestParam String checkIn,
                              @RequestParam String checkOut,
                              RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findBooking(id).orElse(null);
        if (booking == null) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy đặt phòng!");
            return "redirect:/bookings/list";
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);

            if (!checkOutDate.isAfter(checkInDate)) {
                redirectAttributes.addFlashAttribute("message", "Ngày trả phải sau ngày nhận!");
                return "redirect:/bookings/edit?id=" + id;
            }

            Room room = roomService.findRoom(roomNumber).orElse(null);
            if (room == null) {
                redirectAttributes.addFlashAttribute("message", "Phòng không tồn tại!");
                return "redirect:/bookings/edit?id=" + id;
            }

            booking.setRoom(room);
            booking.setCheckIn(checkInDate);
            booking.setCheckOut(checkOutDate);

            boolean success = bookingService.updateBooking(booking);
            redirectAttributes.addFlashAttribute("message", success ? "Cập nhật thành công!" : "Cập nhật thất bại!");

        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("message", "Ngày không hợp lệ!");
            return "redirect:/bookings/edit?id=" + id;
        }

        return "redirect:/bookings/list";
    }


    // Giao diện nhập ID để tìm kiếm đặt phòng
    @GetMapping("/search/form")
    public String showSearchBookingForm() {
        return "search-booking";
    }

    // Xử lý tìm kiếm đặt phòng theo ID
    @PostMapping("/search")
    public String searchBookingById(@RequestParam Long id, Model model) {
        Booking booking = bookingService.findBooking(id).orElse(null);
        if (booking == null) {
            model.addAttribute("message", "Không tìm thấy đặt phòng với ID đã nhập.");
        } else {
            model.addAttribute("booking", booking);
        }
        return "search-booking";
    }



}
