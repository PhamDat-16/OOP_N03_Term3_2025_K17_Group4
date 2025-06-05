package HotelDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GuestDAO {
    public boolean addGuest(String name, String identification, String phone) {
        String sql = "INSERT INTO guests (name, identification, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, identification);
            stmt.setString(3, phone);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khách hàng: " + e.getMessage());
            return false;
        }
    }
}