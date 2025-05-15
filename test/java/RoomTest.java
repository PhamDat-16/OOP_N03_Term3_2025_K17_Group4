package test.java;

import main.java.model.Room;

public class RoomTest {
    public static void main(String[] args) {
        Room room = new Room(101, "Deluxe");

        System.out.println("Phòng: " + room.getRoomNumber() + " - Có sẵn: " + room.isAvailable());

        room.setAvailable(false);
        System.out.println("Phòng sau khi đặt: Có sẵn: " + room.isAvailable());
    }
}
