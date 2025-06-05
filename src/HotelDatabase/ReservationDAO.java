package HotelDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDAO {
    public boolean addReservation(String guestName, String chamberId, String checkinDate, String checkoutDate) {
        String sql = "INSERT INTO reservations (guest_name, chamber_id, checkin_date, checkout_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, guestName);
            stmt.setString(2, chamberId);
            stmt.setString(3, checkinDate);
            stmt.setString(4, checkoutDate);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm đặt phòng: " + e.getMessage());
            return false;
        }
    }
}