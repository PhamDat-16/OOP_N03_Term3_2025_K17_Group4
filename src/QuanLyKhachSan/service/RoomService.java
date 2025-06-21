package QuanLyKhachSan.service;

import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import QuanLyKhachSan.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public boolean addRoom(Room room) {
        if (roomRepository.existsById(room.getRoomNumber())) {
            return false;
        }
        roomRepository.save(room);
        return true;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public void updateRoomAvailability(int roomNumber, boolean available) {
        Room room = roomRepository.findById(roomNumber).orElse(null);
        if (room != null) {
            room.setAvailable(available);
            roomRepository.save(room);
        }
    }
}