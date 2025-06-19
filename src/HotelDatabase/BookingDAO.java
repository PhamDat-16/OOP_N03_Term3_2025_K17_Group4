package HotelDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class BookingDAO {
    public boolean addReservation(String guestName, String roomNumber, String checkinDate, String checkoutDate) {
        if (guestName == null || roomNumber == null || checkinDate == null || checkoutDate == null ||
                guestName.trim().isEmpty() || roomNumber.trim().isEmpty() || checkinDate.trim().isEmpty() || checkoutDate.trim().isEmpty()) {
            System.err.println("Dữ liệu đầu vào không hợp lệ");
            return false;
        }

        String sql = "INSERT INTO reservations (guest_name, room_number, checkin_date, checkout_date) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, guestName);
            stmt.setString(2, roomNumber);

            // Chuyển đổi ngày tháng
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date checkin = null;
            java.sql.Date checkout = null;
            try {
                checkin = new java.sql.Date(sdf.parse(checkinDate).getTime());
                checkout = new java.sql.Date(sdf.parse(checkoutDate).getTime());
            } catch (Exception e) {
                System.err.println("Lỗi định dạng ngày: " + e.getMessage());
                return false;
            }
            stmt.setDate(3, checkin);
            stmt.setDate(4, checkout);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.err.println("Không có hàng nào được thêm vào bảng reservations");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm đặt phòng: " + e.getMessage());
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