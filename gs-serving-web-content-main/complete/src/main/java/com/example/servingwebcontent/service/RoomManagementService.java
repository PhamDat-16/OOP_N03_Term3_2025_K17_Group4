package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomManagementService {

    List<Room> getAllRooms();

    List<Room> getAvailableRooms();

    boolean addRoom(Room room);

    boolean updateRoom(Room room);

    boolean deleteRoom(int roomNumber);

    Optional<Room> findRoom(int roomNumber);
}
