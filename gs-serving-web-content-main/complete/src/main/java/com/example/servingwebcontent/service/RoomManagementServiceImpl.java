package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("roomManagementServiceImpl") // Đặt tên bean rõ ràng để tránh trùng
public class RoomManagementServiceImpl implements RoomManagementService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomManagementServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepository.findAll().stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addRoom(Room room) {
        if (roomRepository.existsById(room.getRoomNumber())) {
            return false; // Phòng đã tồn tại
        }
        room.setAvailable(true); // Khi thêm phòng mặc định là còn trống
        roomRepository.save(room);
        return true;
    }

    @Override
    public boolean updateRoom(Room room) {
        if (!roomRepository.existsById(room.getRoomNumber())) {
            return false;
        }
        roomRepository.save(room);
        return true;
    }

    @Override
    public boolean deleteRoom(int roomNumber) {
        Optional<Room> roomOpt = roomRepository.findById(roomNumber);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            if (!room.isAvailable()) return false; // Nếu đang được đặt thì không xóa
            roomRepository.deleteById(roomNumber);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Room> findRoom(int roomNumber) {
        System.out.println("Querying room with number: " + roomNumber);
        try {
            Optional<Room> room = roomRepository.findById(roomNumber);
            System.out.println("Query result: " + (room.isPresent() ? room.get() : "null"));
            return room;
        } catch (Exception e) {
            System.out.println("Error finding room: " + e.getMessage());
            return Optional.empty();
        }
    }
}
