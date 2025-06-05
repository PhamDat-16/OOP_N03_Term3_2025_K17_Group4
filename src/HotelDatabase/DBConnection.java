package HotelDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String USER = "Group_4"; // Thay bằng tên người dùng của bạn
    private static final String PASSWORD = "962005"; // Thay bằng mật khẩu của bạn
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL Driver không tìm thấy: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("Kết nối đến MySQL thành công!");
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            return false;
        }
    }
}