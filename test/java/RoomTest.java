package test;

import model.Room;

public class RoomTest {
    public static void TestRoom() {
        HotelManagement hotel = new HotelManagement();

        // In danh sách phòng
        System.out.println("Danh sách phòng:");
        for (Room room : hotel.getRooms()) {
            System.out.println("Phòng: " + room.getRoom() +
                    ", Loại: " + room.getType() +
                    ", Trạng thái: " + (room.isAvailable() ? "Trống" : "Đã đặt"));
        }

        // Kiểm tra trạng thái của một phòng cụ thể (ví dụ: phòng 201)
        Room room201 = hotel.getRooms().stream()
                .filter(r -> r.getRoom() == 201)
                .findFirst()
                .orElse(null);

        if (room201 != null) {
            System.out.println("\nTrạng thái phòng 201: " +
                    (room201.isAvailable() ? "Trống" : "Đã đặt"));
        } else {
            System.out.println("\nPhòng 201 không tồn tại!");
        }
    }
}