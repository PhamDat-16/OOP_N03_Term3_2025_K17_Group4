package QuanLyKhachSan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
public class Room {
    @Id
    private int roomNumber;
    private String type;
    private boolean available;

    public Room(int roomNumber, String type, boolean available) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = available;
    }
}