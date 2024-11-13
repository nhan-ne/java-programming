package resource.code.View;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import resource.code.Model.PotionManager;
import resource.code.Model.PotionQDB;

public class PotionRenderer extends BaseRenderer {
    private final PotionManager potionManager;
    private final BufferedImage[] images;
    private final PotionQDB potionQDB;

    // Constructor để khởi tạo renderer với potion manager và kích thước ô
    public PotionRenderer(PotionManager potionManager, int cellSize) {
        super(cellSize);
        this.potionManager = potionManager;  // Gán potion manager
        this.potionQDB = new PotionQDB();    // Khởi tạo PotionQDB để lấy đường dẫn hình ảnh từ cơ sở dữ liệu
        this.images = loadPotionImages();    // Tải hình ảnh potion từ cơ sở dữ liệu
    }

    // Phương thức để tải hình ảnh potions từ cơ sở dữ liệu
    private BufferedImage[] loadPotionImages() {
        Map<String, String> imagePaths = potionQDB.getImagePaths();  // Lấy các đường dẫn hình ảnh từ PotionQDB
        BufferedImage[] loadedImages = new BufferedImage[imagePaths.size()];

        int index = 0;
        for (String path : imagePaths.values()) {
            try {
                loadedImages[index] = loadImage(path); 
            } catch (IOException e) {
                System.err.println("Không thể tải hình ảnh từ đường dẫn: " + path);  
                loadedImages[index] = null; 
            }
            index++;
        }
        return loadedImages; 
    }

    // Tải một hình ảnh đơn từ đường dẫn đã cho
    private BufferedImage loadImage(String path) throws IOException {
        File imageFile = new File(path);
        if (!imageFile.exists()) {
            System.err.println("Tệp không tồn tại: " + path);
            return null;
        }
        return ImageIO.read(imageFile);
    }
    // Phương thức vẽ thức ăn
    @Override
    public void render(Graphics g) {
        // Lấy vị trí các potion từ PotionManager
        Set<Point> potionPositions = potionManager.getPotionPosition();

        // Duyệt qua tất cả các vị trí potion để vẽ từng potion
        for (Point potionPosition : potionPositions) {
            int x = potionPosition.x * cellSize;
            int y = potionPosition.y * cellSize;

            String potionId = potionManager.getPotionIdAt(potionPosition);

            int potionIndex = potionManager.getSelectedPotionIndex();

            // Kiểm tra xem chỉ số potion có hợp lệ và hình ảnh có tồn tại không
            if (potionIndex >= 0 && potionIndex < images.length && images[potionIndex] != null) {
                g.drawImage(images[potionIndex], x, y, cellSize, cellSize, null);
            } else {
                // Nếu không có hình ảnh hợp lệ, vẽ hình ảnh hình tròn màu đỏ
                g.setColor(java.awt.Color.RED);
                g.fillOval(x, y, cellSize, cellSize);
            }
        }
    }
}