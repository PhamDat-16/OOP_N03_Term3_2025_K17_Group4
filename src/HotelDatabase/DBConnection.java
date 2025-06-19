package HotelDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class DBConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/hotel_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER;
    private static final String PASSWORD;
    private static Connection connection = null;

    // Tải cấu hình từ file db.properties
    static {
        Properties props = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("Không tìm thấy file db.properties");
            }
            props.load(input);
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Lỗi tải cấu hình: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }

    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            System.out.println("Kết nối đến MySQL thành công!");
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi đóng kết nối: " + ex.getMessage());
                }
            }
        }
    }
}