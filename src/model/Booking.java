package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Booking {
    @Id
    private String bookingId; // Bạn cần thêm trường ID duy nhất
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;

    // Constructors, getters, setters
    public Booking() {}
    public Booking(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
}