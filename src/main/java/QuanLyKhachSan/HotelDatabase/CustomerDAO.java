package QuanLyKhachSan.HotelDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
    public boolean addCustomer(String name, String idcard, String phone) {
        if (name == null || idcard == null || phone == null ||
                name.trim().isEmpty() || idcard.trim().isEmpty() || phone.trim().isEmpty()) {
            System.err.println("Dữ liệu đầu vào không hợp lệ");
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO customers (name, idcard, phone_number) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, idcard);
            stmt.setString(3, phone);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.err.println("Không có hàng nào được thêm vào bảng customers");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khách hàng: " + e.getMessage() + ", SQL State: " + e.getSQLState());
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