package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Room {
    @Id
    private int roomNumber;
    private String type;
    private boolean available;

    // Constructors, getters, setters
    public Room(int roomNumber, String type, String single, double v, boolean b) {}
    public Room(int roomNumber, String type, boolean b) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = true;
    }

    public Room() {

    }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}