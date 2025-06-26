package QuanLyKhachSan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", unique = true, nullable = false)
    private int roomNumber;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "available", nullable = false)
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
    public Long getId() {
        return id;
    }

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
