package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Booking;
import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.service.BookingManagementService;
import com.example.servingwebcontent.service.RoomManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final RoomManagementService roomManagementService;
    private final BookingManagementService bookingManagementService;

    public RoomController(RoomManagementService roomManagementService, BookingManagementService bookingManagementService) {
        if (roomManagementService == null || bookingManagementService == null) {
            throw new IllegalArgumentException("RoomManagementService or BookingManagementService cannot be null");
        }
        this.roomManagementService = roomManagementService;
        this.bookingManagementService = bookingManagementService;
    }

    @GetMapping("/active-rooms")
    public String showActiveRooms(Model model) {
        try {
            List<Booking> bookings = bookingManagementService.getBookings().stream()
                    .filter(b -> b != null && b.getCustomer() != null && b.getRoom() != null)
                    .collect(Collectors.toList());
            model.addAttribute("bookings", bookings);
            logger.info("Đã tải {} đặt phòng đang hoạt động", bookings.size());
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách phòng đang hoạt động: {}", e.getMessage(), e);
            model.addAttribute("message", "Lỗi khi tải danh sách phòng: " + e.getMessage());
            model.addAttribute("bookings", new ArrayList<>());
        }
        return "active-rooms";
    }

    @GetMapping("/rooms")
    public String showAllRooms(Model model) {
        try {
            List<Room> rooms = roomManagementService.getRooms().stream()
                    .filter(r -> r != null)
                    .collect(Collectors.toList());
            model.addAttribute("rooms", rooms);
            logger.info("Đã tải {} phòng", rooms.size());
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách phòng: {}", e.getMessage(), e);
            model.addAttribute("message", "Lỗi khi tải danh sách phòng: " + e.getMessage());
            model.addAttribute("rooms", new ArrayList<>());
        }
        return "rooms";
    }
}