package springboot.controller;

import model.Booking;
import model.Customer;
import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.service.HotelManagementService;

import java.time.LocalDate;

@Controller
public class BookingController {
    @Autowired
    private HotelManagementService hotelService;

    @GetMapping("/bookings")
    public String getBookings(Model model) {
        model.addAttribute("bookings", hotelService.getBookings());
        return "bookings";
    }

    @PostMapping("/addBooking")
    public String addBooking(
            @RequestParam String name,
            @RequestParam String idCard,
            @RequestParam String phone,
            @RequestParam int roomNumber,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            Model model) {

        Customer customer = new Customer(name, idCard, phone);
        Room room = hotelService.getRooms().stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
        if (room == null || !room.isAvailable()) {
            model.addAttribute("message", "Phòng không tồn tại hoặc đã được đặt!");
            model.addAttribute("bookings", hotelService.getBookings());
            return "bookings";
        }

        Booking booking = new Booking(customer, room, LocalDate.parse(checkIn), LocalDate.parse(checkOut));
        if (hotelService.addBooking(booking)) {
            model.addAttribute("message", "Đặt phòng thành công!");
        } else {
            model.addAttribute("message", "Đặt phòng thất bại!");
        }
        model.addAttribute("bookings", hotelService.getBookings());
        return "bookings";
    }
}