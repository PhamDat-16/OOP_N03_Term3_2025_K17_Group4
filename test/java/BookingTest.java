
package test.java;

import main.java.model.Booking;
import main.java.model.Customer;
import main.java.model.Room;
import main.java.model.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookingTest {
    private List<Booking> bookings = new ArrayList<>();

    public void createBooking(Customer customerId, Room roomId, LocalDate checkIn, LocalDate checkOut , int numberOfGuests) {
        if (!roomId.isAvailable()) {
            System.out.println("Phong da duoc dat.");
            return;
        }

        Booking booking = new Booking(customerId, roomId, checkIn, checkOut , numberOfGuests);
        bookings.add(booking);
        roomId.setAvailable(false);
        System.out.println("Dat phong thanh cong! Booking ID: " + booking.getBookingId());
    }
    public Booking findBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }
    public boolean checkOut(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                booking.getRoom().setAvailable(true);
                System.out.println("Check-out thanh cong.");
                return true;
            }
        }
        System.out.println("Khong tim thay booking ID.");
        return false;
    }

    public void cancelBooking(int bookingId) {
        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (booking.getBookingId() == bookingId) {
                booking.getRoom().setAvailable(true);
                iterator.remove();
                System.out.println("Da huy booking.");
                return;
            }
        }
        System.out.println("Khong tim thay booking.");
    }

    // public void printInvoice(int bookingId) {
    //     for (Booking booking : bookings) {
    //         if (booking.getBookingId() == bookingId) {
    //             long total = booking.calculateTotal();
    //             System.out.println("\n===== HOA DON =====");
    //             System.out.println("Khach: " + booking.getCustomer().getName());
    //             System.out.println("Phong: " + booking.getRoom().getRoomNumber());
    //             System.out.println("Tu: " + booking.getCheckInDate() + " -> " + booking.getCheckOutDate());
    //             System.out.println("Tong tien: " + total + " VND");
    //             return;
    //         }
    //     }
    //     System.out.println("Khong tim thay booking.");
    // }

    public void listBookings() {
        System.out.println("\nDanh sach dat phong:");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }
    public void addServiceToBooking(int bookingId, Service service) {
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            booking.addService(service);
            System.out.println("Đã thêm dịch vụ vào booking.");
        } else {
            System.out.println("Không tìm thấy booking.");
        }
    }
    public Booking findLatestBookingByCustomer(int customerId) {
        Booking latest = null;
        for (Booking b : bookings) {
            if (b.getCustomer().getId() == customerId) {
                if (latest == null || b.getBookingId() > latest.getBookingId()) {
                    latest = b;
                }
            }
        }
        return latest;
    }
    public List<Booking> getAllBookings() {
        return bookings;
    }
    public boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        for (Booking b : bookings) {
            if (b.getRoom().getRoomNumber() == room.getRoomNumber()
                && b.getBookingStatus() != Booking.BookingStatus.CANCELLED
                && b.getBookingStatus() != Booking.BookingStatus.CHECKED_OUT) {
                
                if (!(checkOut.isBefore(b.getCheckInDate()) || checkIn.isAfter(b.getCheckOutDate()))) {
                    return false; // Có trùng lịch => phòng không rảnh
                }
            }
        }
        return true;
    }
    public boolean isRoomAvailable(Room room) {
        for (Booking b : bookings) {
            if (b.getRoom().getRoomNumber() == room.getRoomNumber()
                && b.getBookingStatus() != Booking.BookingStatus.CANCELLED
                && b.getBookingStatus() != Booking.BookingStatus.CHECKED_OUT) {
                return false; // phòng đang được đặt
            }
        }
        return true; // phòng trống
    }  
    public void printInvoice(int bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.out.println("Khong tim thay booking.");
            return;
        }

        System.out.println("=== HOA DON DAT PHONG ===");
        System.out.println("Khach hang: " + booking.getCustomer().getName());
        System.out.println("Phong: " + booking.getRoom().getRoomNumber());
        System.out.println("Check-in: " + booking.getCheckInDate());
        System.out.println("Check-out: " + booking.getCheckOutDate());

        long days = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        double roomCost = days * booking.getRoom().getPricePerNight();

        System.out.println("So ngay o: " + days);
        System.out.println("Gia phong/dem: " + booking.getRoom().getPricePerNight());
        System.out.println("Tien phong: " + roomCost + " VND");

        System.out.println("--- DICH VU SU DUNG ---");
        double totalServiceCost = 0;
        for (Service s : booking.getServices()) {
            double cost = s.getPrice() * s.getQuantity();
            totalServiceCost += cost;
            System.out.println(s.getServiceName() + " x " + s.getQuantity() + " = " + cost + " VND");
        }
        System.out.println("Tong tien dich vu: " + totalServiceCost + " VND");

        double total = roomCost + totalServiceCost;
        System.out.println("=== TONG TIEN PHONG + DICH VU: " + total + " VND ===");
    }
       
}

