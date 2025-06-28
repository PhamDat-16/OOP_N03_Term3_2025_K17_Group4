package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomManagementService {
    private static final Logger logger = LoggerFactory.getLogger(RoomManagementService.class);

    @Autowired
    private RoomRepository roomRepository;

    @PostConstruct
    public void initializeRooms() {
        if (roomRepository.count() == 0) {
            String[] roomTypes = {"Đơn", "Đôi"};
            for (int floor = 2; floor <= 6; floor++) {
                for (int i = 1; i <= 3; i++) {
                    int roomNumber = floor * 100 + i;
                    String type = roomTypes[(roomNumber % 2 == 0) ? 1 : 0];
                    Room room = new Room(roomNumber, type, true);
                    roomRepository.save(room);
                }
            }
            logger.info("Đã khởi tạo {} phòng.", roomRepository.count());
        }
    }

    public List<Room> getRooms() {
        return roomRepository.findAll().stream()
                .filter(room -> room != null)
                .collect(Collectors.toList());
    }
}