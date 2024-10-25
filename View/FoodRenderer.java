package resource.code.View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import resource.code.Model.FoodManager;

public class FoodRenderer extends BaseRenderer {
    private final FoodManager foodModel;
    private final BufferedImage[] images = new BufferedImage[8];
    private final String[] imagePaths = {
        "/image/food/Pineapple.png",
        "/image/food/Watermelon.png",
        "/image/food/Apple.png",    
        "/image/food/Banana.png",   
        "/image/food/Orange.png",
        "/image/food/Cherry.png",
        "/image/food/Mulberry.png",
        "/image/food/Lemon.png"
    };

    public FoodRenderer(FoodManager foodModel, int cellSize) {
        super(cellSize);
        this.foodModel = foodModel;
        loadImages();
    }

    private void loadImages() {
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(getClass().getResource(imagePaths[i]));
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Không thể tải hình ảnh từ đường dẫn: " + imagePaths[i]);
                images[i] = null;
            }
        }
    }
    
    @Override
    public void render(Graphics g) {
        int[][] positions = foodModel.getPosition();
        int[] selectedFood = foodModel.getSelectedFood();
        for (int i = 0; i < selectedFood.length; i++) {
            int index = selectedFood[i];
            if (images[index] != null) {
                g.drawImage(images[index], positions[i][0] * cellSize, positions[i][1] * cellSize, cellSize, cellSize, null);
            } else {
                g.setColor(java.awt.Color.RED);
                g.fillOval(positions[i][0] * cellSize, positions[i][1] * cellSize, cellSize, cellSize);
            }
        }
    }
}
