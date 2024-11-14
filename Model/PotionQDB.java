package resource.code.Model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PotionQDB {
    private static final Logger LOGGER = Logger.getLogger(PotionQDB.class.getName());
    private final DatabaseConnect dbConnect;
    private Map<String, String> imagePathsCache;
    private Map<String, String> infosCache;
    private Map<String, String> namesCache;  // Cache lưu tên potion

    public PotionQDB() {
        dbConnect = new DatabaseConnect();
        loadData();
    }

    private synchronized void loadData() {
        if (imagePathsCache == null && infosCache == null && namesCache == null) {
            imagePathsCache = new HashMap<>();
            infosCache = new HashMap<>();
            namesCache = new HashMap<>();  // Khởi tạo cache cho tên potion
            loadPotionData();
        }
    }

    private void loadPotionData() {
        String sqlPotionData = "SELECT idPotion, Name, pathImage, info FROM potions";  // Thêm namePotion vào câu lệnh SQL
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlPotionData);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String idPotion = rs.getString("idPotion");
                String namePotion = rs.getString("Name");  // Lấy tên potion
                String path = rs.getString("pathImage");
                String info = rs.getString("info");

                if (isImagePathValid(path)) {
                    imagePathsCache.put(idPotion, path);
                    infosCache.put(idPotion, info);
                    namesCache.put(idPotion, namePotion);  // Lưu tên potion vào cache
                } else {
                    LOGGER.log(Level.WARNING, MessageFormat.format("Đường dẫn ảnh không hợp lệ: {0}", path));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải thông tin potion: ", e);
        }
    }

    private boolean isImagePathValid(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return false;
        }
        File file = new File(imagePath);
        return file.exists() && imagePath.matches(".*\\.(jpg|jpeg|png|gif)$");
    }

    public Map<String, String> getImagePaths() {
        return imagePathsCache;
    }

    public Map<String, String> getPotionInfos() {
        return infosCache;
    }

    public Map<String, String> getPotionNames() {
        return namesCache;  // Phương thức mới để trả về tên potion
    }

    public List<String> getPotionIds() {
        return List.copyOf(imagePathsCache.keySet());
    }

    public String getIdPotionByIndex(int index) {
        String[] ids = imagePathsCache.keySet().toArray(new String[0]);
        if (index < 0 || index >= ids.length) {
            LOGGER.log(Level.WARNING, MessageFormat.format("Chỉ số không hợp lệ: {0}", index));
            return null;
        }
        return ids[index];
    }

    public String getPotionInfoById(String idPotion) {
        return infosCache.get(idPotion);
    }

    public String getPotionNameById(String idPotion) {
        return namesCache.get(idPotion);  // Lấy tên potion theo ID
    }

    public Map<String, String> getPotionDetailsById(String idPotion) {
        Map<String, String> potionDetails = new HashMap<>();
        String name = namesCache.get(idPotion);  // Lấy tên potion từ cache
        String imagePath = imagePathsCache.get(idPotion);
        String potionInfo = infosCache.get(idPotion);
        
        if (name != null && imagePath != null && potionInfo != null) {
            potionDetails.put("name", name);
            potionDetails.put("imagePath", imagePath);
            potionDetails.put("info", potionInfo);
        } else {
            LOGGER.log(Level.WARNING, MessageFormat.format("Details not found for potion ID: {0}", idPotion));
        }
        return potionDetails;
    }

    public synchronized void refreshData() {
        imagePathsCache.clear();
        infosCache.clear();
        namesCache.clear();  // Xóa cache của tên potion
        loadData();
    }
}
