package test.java;

public class Room {
    public static void main(String[] args) {
        Room room = new Room(101, "Deluxe");

        System.out.println("Phòng: " + roomNumber + " - " + room.isAvailable());

        room.setAvailable(false);
        System.out.println("Phòng sau khi đặt: " + room.isAvailable());
    }
}

