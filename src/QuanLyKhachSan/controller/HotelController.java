package QuanLyKhachSan.controller;

import QuanLyKhachSan.model.Booking;
import QuanLyKhachSan.model.Customer;
import QuanLyKhachSan.model.Room;
import QuanLyKhachSan.service.HotelManagement;
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
public class HotelController {
    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);
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

    @GetMapping("/active-rooms")
    public String showActiveRooms(Model model) {
        try {
            List<Booking> bookings = hotelManagement.getBookings().stream()
                    .filter(b -> b != null && b.getCustomer() != null && b.getRoom() != null)
                    .collect(Collectors.toList());
            model.addAttribute("bookings", bookings);
            logger.info("Đã tải {} đặt phòng đang hoạt động", bookings.size());
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách phòng đang hoạt động: {}", e.getMessage());
            model.addAttribute("message", "Lỗi khi tải danh sách phòng!");
            model.addAttribute("bookings", new ArrayList<>());
        }
        return "active-rooms";
    }

    @GetMapping("/rooms")
    public String showAllRooms(Model model) {
        try {
            List<Room> rooms = hotelManagement.getRooms().stream()
                    .filter(r -> r != null)
                    .collect(Collectors.toList());
            model.addAttribute("rooms", rooms);
            logger.info("Đã tải {} phòng", rooms.size());
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách phòng: {}", e.getMessage());
            model.addAttribute("message", "Lỗi khi tải danh sách phòng!");
            model.addAttribute("rooms", new ArrayList<>());
        }
        return "rooms";
    }

    private boolean isInvalidInput(String name, String idCard, String phone) {
        return name == null || name.trim().isEmpty() ||
                idCard == null || idCard.trim().isEmpty() || !idCard.matches("\\d{9,12}") ||
                phone == null || phone.trim().isEmpty() || !phone.matches("\\d{10,11}");
    }
}