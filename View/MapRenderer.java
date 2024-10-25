package resource.code.View;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import resource.code.Model.GameModel;

public class MapRenderer extends BaseRenderer {
    private final GameModel model;
    private final Image[] images = new Image[3]; // Mảng hình ảnh cho bản đồ

    public MapRenderer(GameModel model, int cellSize) {
        super(cellSize);
        this.model = model;
        
        // Tải hình ảnh bản đồ
        try {
            images[0] = ImageIO.read(getClass().getResource("/image/map/square1.png"));
            images[1] = ImageIO.read(getClass().getResource("/image/map/square2.png"));
            images[2] = ImageIO.read(getClass().getResource("/image/map/square3.png"));
        } catch (IOException e) {
            System.err.println("Lỗi tải hình ảnh bản đồ: " + e.getMessage());
        }
    }

    @Override
    public void render(Graphics g) {
        // Vẽ các ô
        for (int row = 0; row < model.getRows(); row++) {
            for (int col = 0; col < model.getCols(); col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                // Vẽ viền
                if (model.isBorder(row, col)) {
                    g.drawImage(images[2], x, y, cellSize, cellSize, null);
                } else {
                    // Vẽ ô nền xen kẽ
                    if ((row + col) % 2 == 0) {
                        g.drawImage(images[0], x, y, cellSize, cellSize, null); // square1
                    } else {
                        g.drawImage(images[1], x, y, cellSize, cellSize, null); // square2
                    }
                }
            }
        }
    }

    // Method to get the number of columns
    public int getCols() {
        return model.getCols();
    }

    // Method to get the number of rows
    public int getRows() {
        return model.getRows();
    }
}
