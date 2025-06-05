package HotelDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChamberDAO {
    public boolean addChamber(String chamberId, String type, String status) {
        String sql = "INSERT INTO chambers (chamber_id, type, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chamberId);
            stmt.setString(2, type);
            stmt.setString(3, status);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm phòng: " + e.getMessage());
            return false;
        }
    }
}