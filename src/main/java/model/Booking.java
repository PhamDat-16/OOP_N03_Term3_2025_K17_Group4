package main.java.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        CHECKED_OUT
    }
    private static int nextId = 1;
    private int bookingId;
    private Customer customerId;
    private Room roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private BookingStatus bookingStatus;

    public Booking(Customer customerId, Room roomId, LocalDate checkInDate, LocalDate checkOutDate , int numberOfGuests) {
        this.bookingId = nextId++;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.bookingStatus = BookingStatus.PENDING;
    }

    public int getBookingId() { return bookingId; }
    public Customer getCustomer() { return customerId; }
    public Room getRoom() { return roomId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public int getNumberOfGuests() { return numberOfGuests;}
    public BookingStatus getBookingStatus() { return bookingStatus;}
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests;}
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus;}
    public long calculateTotalServices() {
        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return (long)(roomId.getPricePerNight() * days);
    }
    private List<Service> services = new ArrayList<>();

public void addService(Service service) {
    services.add(service);
}

public List<Service> getServices() {
    return services;
}

public double calculateServiceTotal() {
    double total = 0;
    for (Service s : services) {
        total += s.getTotalPrice();
    }
    return total;
}

public long calculateTotal() {
    long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    if (days == 0) days = 1; // ở trong ngày vẫn tính 1 ngày
    double roomCost = days * roomId.getPricePerNight();
    return (long) (roomCost + calculateServiceTotal());
}
    @Override
    public String toString() {
        return "Booking ID: " + bookingId + " | Khach: " + customerId.getName() +
               " | Phong: " + roomId.getRoomNumber() +
               " | Tu: " + checkInDate + " -> " + checkOutDate;
    }
}
