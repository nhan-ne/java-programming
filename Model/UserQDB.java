package resource.code.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserQDB {
    // Lấy điểm cao nhất từ cơ sở dữ liệu cho người chơi thông qua PhoneNumber
    public static int getHighScore(String phoneNumber) {
        String sql = "SELECT HighScore FROM user WHERE PhoneNumber = ?";
        int highScore = 0;  // Mặc định là 0 nếu không tìm thấy
    
        try (Connection conn = new DatabaseConnect().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phoneNumber);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                highScore = rs.getInt("HighScore");
            } else {
                System.out.println("Không tìm thấy người dùng với PhoneNumber: " + phoneNumber);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn điểm cao nhất: " + e.getMessage());
        }
    
        return highScore;
    }    

    // Lưu điểm cao nhất vào cơ sở dữ liệu với PhoneNumber là khóa chính
    public static void saveHighScore(User user) {
        String sql = "INSERT INTO user (PhoneNumber, HighScore) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE HighScore = VALUES(HighScore)";

        try (Connection conn = new DatabaseConnect().getConnection(); // Kết nối cơ sở dữ liệu
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getPhoneNumber()); // PhoneNumber là khóa chính
            stmt.setInt(2, user.getHighScore());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi lưu điểm cao nhất: " + e.getMessage());
        }
    }

    public static List<User> getTopHighScores() {
        List<User> topHighScores = new ArrayList<>();
        String sql = "SELECT PhoneNumber, HighScore FROM user ORDER BY HighScore DESC LIMIT 6"; // Lấy 6 người chơi có điểm cao nhất
    
        try (Connection conn = new DatabaseConnect().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String phoneNumber = rs.getString("PhoneNumber");
                int highScore = rs.getInt("HighScore");
                topHighScores.add(new User(phoneNumber, highScore)); // Pass both phoneNumber and highScore
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving high scores: " + e.getMessage());
        }
    
        return topHighScores;
    }
    
}
