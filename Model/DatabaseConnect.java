package resource.code.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SnakeGame";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Tải driver JDBC một lần tại thời điểm khởi động
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver JDBC đã được tải thành công!");
        } catch (ClassNotFoundException e) {
            System.out.println("Lỗi tải driver: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            // Kết nối tới cơ sở dữ liệu và trả về kết nối
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Kết nối Database thành công!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            return null; // Trả về null nếu không thể kết nối
        }
    }
}
