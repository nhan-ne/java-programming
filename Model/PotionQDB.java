package resource.code.Model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PotionQDB {
    private static final Logger LOGGER = Logger.getLogger(PotionQDB.class.getName());
    private final DatabaseConnect dbConnect;
    private Map<String, String> imagePathsCache;

    public PotionQDB() {
        dbConnect = new DatabaseConnect();
        loadData();  // Tải dữ liệu từ cơ sở dữ liệu
    }

    // Tải dữ liệu từ cơ sở dữ liệu
    private synchronized void loadData() {
        if (imagePathsCache == null) {
            imagePathsCache = new HashMap<>();
            loadImagePaths();  // Tải đường dẫn hình ảnh từ cơ sở dữ liệu
        }
    }

    // Tải dữ liệu đường dẫn hình ảnh của potion từ cơ sở dữ liệu
    private void loadImagePaths() {
        String sqlImagePath = "SELECT idPotion, pathImage FROM potions";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlImagePath);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String idPotion = rs.getString("idPotion");
                String path = rs.getString("pathImage");
                if (isImagePathValid(path)) {
                    imagePathsCache.put(idPotion, path);  // Lưu đường dẫn hình ảnh của potion vào cache nếu hợp lệ
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
            return false;
        }
        File file = new File(imagePath);
        return file.exists() && imagePath.matches(".*\\.(jpg|jpeg|png|gif)$");  // Kiểm tra xem tệp có tồn tại và có định dạng hợp lệ không
    }

    public Map<String, String> getImagePaths() {
        return imagePathsCache;  // Trả về danh sách đường dẫn hình ảnh của các potion
    }

    // Phương thức trả về danh sách ID các loại potion
    public List<String> getPotionIds() {
        return new ArrayList<>(imagePathsCache.keySet());  // Trả về danh sách các ID của các loại potion
    }

    // Lấy idPotion theo chỉ số
    public String getIdPotionByIndex(int index) {
        String[] ids = imagePathsCache.keySet().toArray(new String[0]);
        if (index < 0 || index >= ids.length) {
            LOGGER.log(Level.WARNING, MessageFormat.format("Chỉ số không hợp lệ: {0}", index));  // Cảnh báo nếu chỉ số không hợp lệ
            return null;
        }
        return ids[index];  // Trả về idPotion tương ứng với chỉ số
    }

    // Làm mới dữ liệu (xóa cache và tải lại dữ liệu)
    public synchronized void refreshData() {
        imagePathsCache.clear();
        loadData(); 
    }
}
