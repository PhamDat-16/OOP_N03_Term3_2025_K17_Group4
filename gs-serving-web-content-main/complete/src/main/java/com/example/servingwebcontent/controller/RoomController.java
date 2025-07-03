package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.service.RoomManagementService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomManagementService roomService;

    public RoomController(@Qualifier("roomManagementServiceImpl") RoomManagementService roomService) {
        this.roomService = roomService;
    }
    @GetMapping
    public String showRoomMenu() {
        return "rooms";
    }

    @GetMapping("/list")
    public String showRoomList(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms-list";
    }


    // Giao diện thêm phòng
    @GetMapping("/add")
    public String showAddForm() {
        return "add-room";
    }

    // Xử lý thêm phòng
    @PostMapping("/add")
    public String addRoom(@RequestParam int roomNumber,
                          @RequestParam String typeRoom,
                          @RequestParam double price,
                          RedirectAttributes redirectAttributes) {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setTypeRoom(typeRoom);
        room.setPrice(price);
        boolean success = roomService.addRoom(room);
        redirectAttributes.addFlashAttribute("message", success ? "Thêm phòng thành công!" : "Phòng đã tồn tại!");
        return "redirect:/rooms/list";
    }

    // Giao diện sửa phòng
    @GetMapping("/edit")
    public String showEditForm(@RequestParam int roomNumber, Model model) {
        Room room = roomService.findRoom(roomNumber).orElse(null);
        if (room == null) {
            model.addAttribute("message", "Không tìm thấy phòng!");
            return "redirect:/rooms/list";
        }
        model.addAttribute("room", room);
        return "edit-room";
    }

    // Xử lý sửa phòng
    @PostMapping("/edit")
    public String editRoom(@RequestParam int roomNumber,
                           @RequestParam String typeRoom,
                           @RequestParam double price,
                           RedirectAttributes redirectAttributes) {
        Room room = roomService.findRoom(roomNumber).orElse(null);
        if (room == null) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy phòng!");
            return "redirect:/rooms/list";
        }
        room.setTypeRoom(typeRoom);
        room.setPrice(price);
        boolean updated = roomService.updateRoom(room);
        redirectAttributes.addFlashAttribute("message", updated ? "Cập nhật phòng thành công!" : "Cập nhật thất bại!");
        return "redirect:/rooms/list";
    }

    // Giao diện xóa phòng
    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam int roomNumber, Model model) {
        Room room = roomService.findRoom(roomNumber).orElse(null);
        if (room == null) {
            model.addAttribute("message", "Không tìm thấy phòng!");
            return "redirect:/rooms/list";
        }
        model.addAttribute("room", room);
        return "delete-room";
    }

    // Xử lý xóa phòng
    @PostMapping("/delete")
    public String deleteRoom(@RequestParam int roomNumber, RedirectAttributes redirectAttributes) {
        boolean deleted = roomService.deleteRoom(roomNumber);
        redirectAttributes.addFlashAttribute("message",
                deleted ? "Xóa phòng thành công!" : "Không thể xóa phòng vì đang được đặt hoặc không tồn tại!");
        return "redirect:/rooms/list";
    }

    // Giao diện tìm kiếm phòng
    @GetMapping("/search")
    public String showSearchForm() {
        return "search-room";
    }

    // Xử lý tìm kiếm phòng theo số phòng
    @PostMapping("/search")
    public String searchRoom(@RequestParam int roomNumber, Model model) {
        System.out.println("Searching for room number: " + roomNumber);
        Room room = roomService.findRoom(roomNumber).orElse(null);
        if (room == null) {
            System.out.println("Room not found for number: " + roomNumber);
            model.addAttribute("message", "Không tìm thấy phòng!");
        } else {
            System.out.println("Room found: " + room.getRoomNumber() + ", type: " + room.getTypeRoom());
            model.addAttribute("room", room);
        }
        return "search-room";
    }
}
