package QuanLyKhachSan.model;

public class Room {
    private int roomNumber;
    private String type;
    private boolean available;

    // Constructor mặc định
    public Room() {
    }

    // Constructor chính
    public Room(int roomNumber, String type, boolean available) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = available;
    }

    // Getter và Setter
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}