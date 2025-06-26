package QuanLyKhachSan.HotelDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomDAO {
    public boolean addRoom(String roomNumber, String type, String available) {
        if (roomNumber == null || type == null || available == null ||
                roomNumber.trim().isEmpty() || type.trim().isEmpty() || available.trim().isEmpty()) {
            System.err.println("Dữ liệu đầu vào không hợp lệ");
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO rooms (room_number, type, available) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomNumber);
            stmt.setString(2, type);
            stmt.setString(3, available);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.err.println("Không có hàng nào được thêm vào bảng rooms");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm phòng: " + e.getMessage() + ", SQL State: " + e.getSQLState());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.err.println("Lỗi khi đóng tài nguyên: " + ex.getMessage());
            }
        }
    }
}