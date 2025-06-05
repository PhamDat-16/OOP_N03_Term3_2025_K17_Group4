package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookingManager {
    private List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.getRoom().setAvailable(false); // Khi đặt phòng thì phòng không còn trống
        System.out.println("Đã thêm khách hàng: " + booking.getCustomer().getName());
    }

    public void removeBooking(String keyword) {
        Iterator<Booking> iterator = bookings.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            Booking b = iterator.next();
            Customer c = b.getCustomer();
            if (c.getName().equalsIgnoreCase(keyword) || c.getIdCard().equals(keyword)) {
                iterator.remove();
                b.getRoom().setAvailable(true); // Khi hủy thì phòng trở lại trạng thái trống
                System.out.println("Đã xóa khách hàng: " + c.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy khách hàng với từ khóa: " + keyword);
        }
    }

    public void showAllBookings() {
        for (Booking b : bookings) {
            System.out.println("Tên: " + b.getCustomer().getName() +
                    ", CMND: " + b.getCustomer().getIdCard() +
                    ", Phòng: " + b.getRoom().getRoom());
        }
    }
}
