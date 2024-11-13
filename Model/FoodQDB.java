package resource.code.Model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FoodQDB {
    private static final Logger LOGGER = Logger.getLogger(FoodQDB.class.getName());  // Khởi tạo Logger để ghi lại các thông tin và lỗi
    private final DatabaseConnect dbConnect;  // Kết nối cơ sở dữ liệu
    private Map<String, Integer> foodScoresCache;  // Lưu trữ điểm số của thực phẩm
    private Map<String, String> imagePathsCache;  // Lưu trữ đường dẫn hình ảnh của thực phẩm

    public FoodQDB() {
        dbConnect = new DatabaseConnect();  // Khởi tạo kết nối cơ sở dữ liệu
        loadData();  // Tải dữ liệu từ cơ sở dữ liệu
    }

    // Tải dữ liệu từ cơ sở dữ liệu
    private synchronized void loadData() {
        if (foodScoresCache == null || imagePathsCache == null) {
            foodScoresCache = new HashMap<>();
            imagePathsCache = new HashMap<>();
            
            // Tải dữ liệu điểm số thực phẩm và đường dẫn hình ảnh
            loadFoodScores();
            loadImagePaths();
        }
    }

    // Tải dữ liệu điểm số thực phẩm từ cơ sở dữ liệu
    private void loadFoodScores() {
        String sqlFoodScore = "SELECT idFood, ScoreFood FROM Food";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlFoodScore);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String idFood = rs.getString("idFood");
                int scoreFood = rs.getInt("ScoreFood");
                foodScoresCache.put(idFood, scoreFood);  // Lưu điểm số thực phẩm vào cache
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải điểm thực phẩm: ", e);
        }
    }

    // Tải dữ liệu đường dẫn hình ảnh từ cơ sở dữ liệu
    private void loadImagePaths() {
        String sqlImagePath = "SELECT idFood, pathImage FROM Food";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlImagePath);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String idFood = rs.getString("idFood");
                String path = rs.getString("pathImage");
                if (isImagePathValid(path)) {
                    imagePathsCache.put(idFood, path);  // Lưu đường dẫn hình ảnh vào cache nếu hợp lệ
                } else {
                    LOGGER.log(Level.WARNING, MessageFormat.format("Đường dẫn ảnh không hợp lệ: {0}", path));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải đường dẫn hình ảnh: ", e); 
        }
    }

    // Kiểm tra tính hợp lệ của đường dẫn hình ảnh
    private boolean isImagePathValid(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return false;  // Đường dẫn không hợp lệ nếu null hoặc rỗng
        }
        File file = new File(imagePath);
        return file.exists() && imagePath.matches(".*\\.(jpg|jpeg|png|gif)$");  // Kiểm tra xem tệp có tồn tại và có định dạng hợp lệ không
    }

    public Map<String, String> getImagePaths() {
        return imagePathsCache;  // Trả về danh sách đường dẫn hình ảnh
    }

    public Map<String, Integer> getScoreFood() {
        return foodScoresCache;  // Trả về danh sách điểm số thực phẩm
    }

    // Lấy idFood theo chỉ số
    public String getIdFoodByIndex(int index) {
        String[] ids = foodScoresCache.keySet().toArray(new String[0]);
        if (index < 0 || index >= ids.length) {
            LOGGER.log(Level.WARNING, MessageFormat.format("Chỉ số không hợp lệ: {0}", index));
            return null;
        }
        return ids[index];  // Trả về idFood tương ứng với chỉ số
    }

    // Làm mới dữ liệu (xóa cache và tải lại dữ liệu)
    public synchronized void refreshData() {
        foodScoresCache.clear();
        imagePathsCache.clear();
        loadData(); 
    }
}
