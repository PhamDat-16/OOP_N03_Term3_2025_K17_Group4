package springboot.controller;

import model.Booking;
import model.Customer;
import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.service.HotelManagementService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Controller
public class HotelController {
    @Autowired
    private HotelManagementService hotelManagementService;

    // Trang chính (MainDashboard)
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Trang thêm đặt phòng
    @GetMapping("/bookings/add")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("customers", hotelManagementService.getCustomers());
        model.addAttribute("rooms", hotelManagementService.getRooms());
        return "booking-form";
    }

    @PostMapping("/bookings/add")
    public String addBooking(
            @RequestParam String name,
            @RequestParam String idCard,
            @RequestParam String phone,
            @RequestParam String roomNumber,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            Model model) {
        // Kiểm tra dữ liệu đầu vào
        if (name.isEmpty() || idCard.isEmpty() || phone.isEmpty() || roomNumber.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
            model.addAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            return "booking-form";
        }

        if (!idCard.matches("\\d{9,12}")) {
            model.addAttribute("error", "CMND/CCCD phải là số và có 9-12 chữ số!");
            return "booking-form";
        }

        if (!phone.matches("\\d{10,11}")) {
            model.addAttribute("error", "Số điện thoại phải là số và có 10-11 chữ số!");
            return "booking-form";
        }

        int roomNum;
        try {
            roomNum = Integer.parseInt(roomNumber);
        } catch (NumberFormatException ex) {
            model.addAttribute("error", "Mã phòng phải là số!");
            return "booking-form";
        }

        if (!checkIn.matches("\\d{4}-\\d{2}-\\d{2}") || !checkOut.matches("\\d{4}-\\d{2}-\\d{2}")) {
            model.addAttribute("error", "Ngày phải có định dạng YYYY-MM-DD!");
            return "booking-form";
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
                model.addAttribute("error", "Ngày check-out phải sau ngày check-in!");
                return "booking-form";
            }

            // Kiểm tra khách hàng
            Customer customer = hotelManagementService.getCustomers().stream()
                    .filter(c -> c.getIdCard().equals(idCard))
                    .findFirst()
                    .orElse(null);
            if (customer == null) {
                customer = new Customer(name, idCard, phone);
                hotelManagementService.addCustomer(customer);
            }

            // Kiểm tra phòng
            Room room = hotelManagementService.getRooms().stream()
                    .filter(r -> r.getRoomNumber() == roomNum)
                    .findFirst()
                    .orElse(null);
            if (room == null) {
                model.addAttribute("error", "Phòng " + roomNum + " không tồn tại!");
                return "booking-form";
            }
            if (!room.isAvailable()) {
                model.addAttribute("error", "Phòng " + roomNum + " đã được đặt!");
                return "booking-form";
            }

            // Thêm đặt phòng
            Booking booking = new Booking(customer, room, checkInDate, checkOutDate);
            if (hotelManagementService.addBooking(booking)) {
                model.addAttribute("success", "Đặt phòng thành công!");
            } else {
                model.addAttribute("error", "Lỗi khi đặt phòng!");
            }
        } catch (DateTimeParseException ex) {
            model.addAttribute("error", "Định dạng ngày không hợp lệ!");
        }
        return "booking-form";
    }

    // Trang quản lý khách hàng
    @GetMapping("/customers")
    public String showCustomerForm(Model model) {
        model.addAttribute("customers", hotelManagementService.getCustomers());
        return "customer-form";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam String identifier, Model model) {
        if (identifier.isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập tên hoặc CMND để xóa!");
            model.addAttribute("customers", hotelManagementService.getCustomers());
            return "customer-form";
        }

        if (hotelManagementService.removeCustomer(identifier)) {
            model.addAttribute("success", "Xóa khách hàng thành công!");
        } else {
            model.addAttribute("error", "Không tìm thấy khách hàng với tên hoặc CMND: " + identifier);
        }
        model.addAttribute("customers", hotelManagementService.getCustomers());
        return "customer-form";
    }

    // Trang kiểm tra phòng
    @GetMapping("/rooms")
    public String showRoomForm(Model model) {
        model.addAttribute("rooms", hotelManagementService.getRooms());
        return "room-form";
    }

    // Trang phòng đang hoạt động
    @GetMapping("/active-rooms")
    public String showActiveRoomsForm(Model model) {
        model.addAttribute("bookings", hotelManagementService.getBookings());
        return "active-rooms-form";
    }
}