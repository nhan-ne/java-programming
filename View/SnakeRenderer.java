package resource.code.View;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import resource.code.Model.SnakeManager;

public class SnakeRenderer {
    private final SnakeManager snake;
    private final int cellSize;
    private BufferedImage bodyImage;
    private BufferedImage headUp, headDown, headLeft, headRight;

    public SnakeRenderer(SnakeManager snake, int cellSize) {
        this.snake = snake;
        this.cellSize = cellSize;
        loadImages(); // Tải hình ảnh cho thân và đầu rắn
    }

    // Tải hình ảnh cho thân và đầu rắn ở các hướng khác nhau
    private void loadImages() {
        String[] imagePaths = {
            "/image/Snake/BodySnake.png",
            "/image/Snake/HeadSnakeTop.png",
            "/image/Snake/HeadSnakeDown.png",
            "/image/Snake/HeadSnakeLeft.png",
            "/image/Snake/HeadSnakeRight.png"
        };
        
        try {
            bodyImage = ImageIO.read(getClass().getResource(imagePaths[0]));
            headUp = ImageIO.read(getClass().getResource(imagePaths[1]));
            headDown = ImageIO.read(getClass().getResource(imagePaths[2]));
            headLeft = ImageIO.read(getClass().getResource(imagePaths[3]));
            headRight = ImageIO.read(getClass().getResource(imagePaths[4]));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Không thể tải hình ảnh: " + e.getMessage());
        }
    }

    // Vẽ con rắn theo hướng hiện tại
    public void render(Graphics g) {
        var body = snake.getBody();
        SnakeManager.Direction direction = snake.getDirection(); // Lấy hướng di chuyển của con rắn

        // Chọn hình ảnh đầu con rắn theo hướng hiện tại
        Image headImage = switch (direction) {
            case UP -> headUp;
            case DOWN -> headDown;
            case LEFT -> headLeft;
            case RIGHT -> headRight;
        };

        // Vẽ đầu con rắn
        Point head = body.getFirst();
        g.drawImage(headImage, head.x * cellSize, head.y * cellSize, cellSize, cellSize, null);

        // Vẽ thân con rắn
        for (int i = 1; i < body.size(); i++) {
            Point segment = body.get(i);
            g.drawImage(bodyImage, segment.x * cellSize, segment.y * cellSize, cellSize, cellSize, null);
        }
    }
}
