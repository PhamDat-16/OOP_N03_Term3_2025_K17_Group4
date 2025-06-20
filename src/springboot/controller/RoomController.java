package springboot.controller;

import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.service.HotelManagementService;

@Controller
public class RoomController {
    @Autowired
    private HotelManagementService hotelService;

    @GetMapping("/rooms")
    public String getRooms(Model model) {
        model.addAttribute("rooms", hotelService.getRooms());
        return "rooms";
    }

    @PostMapping("/addRoom")
    public String addRoom(@RequestParam int roomNumber, @RequestParam String type, Model model) {
        Room room = new Room(roomNumber, type);
        if (hotelService.addRoom(room)) {
            model.addAttribute("message", "Thêm phòng thành công!");
        } else {
            model.addAttribute("message", "Phòng đã tồn tại!");
        }
        model.addAttribute("rooms", hotelService.getRooms());
        return "rooms";
    }
}