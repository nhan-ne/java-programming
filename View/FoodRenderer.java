package resource.code.View;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import resource.code.Model.FoodManager;

public class FoodRenderer extends BaseRenderer {
    private final FoodManager foodModel;
    private final BufferedImage[] images;

    public FoodRenderer(FoodManager foodModel, int cellSize) {
        super(cellSize);
        this.foodModel = foodModel;
        this.images = loadImages();  // Tải hình ảnh một lần khi khởi tạo
    }

    // Tải các hình ảnh cho các món ăn
    private BufferedImage[] loadImages() {
        Map<String, String> imagePaths = foodModel.getImagePaths();  // Lấy đường dẫn hình ảnh từ FoodManager
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

    // Tải hình ảnh từ đường dẫn
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
        // Lấy các vị trí của thức ăn từ FoodManager
        Set<Point> positions = foodModel.getPosition();  // Lấy các vị trí thức ăn đã chọn
        int[] selectedFoodIndexes = foodModel.getSelectedFoodIndexes();  // Lấy các chỉ số của thức ăn đã chọn

        // Duyệt qua từng món ăn được chọn và vẽ
        int i = 0;
        for (Point position : positions) {
            int index = selectedFoodIndexes[i];
            int x = position.x * cellSize;
            int y = position.y * cellSize;

            // Kiểm tra chỉ số hợp lệ và vẽ hình ảnh thức ăn nếu có
            if (index >= 0 && index < images.length && images[index] != null) {
                g.drawImage(images[index], x, y, cellSize, cellSize, null);
            } else {
                // Nếu không có hình ảnh, vẽ một vòng tròn màu đỏ
                g.setColor(java.awt.Color.RED);
                g.fillOval(x, y, cellSize, cellSize);
            }
            i++;
        }
    }
}
