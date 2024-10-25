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
        loadImages(); // Load images for snake body and head
    }

    // Load images for the snake's body and head for different directions
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
            System.err.println("Could not load images: " + e.getMessage());
        }
    }

    // Render the snake based on its current direction
    public void render(Graphics g) {
        var body = snake.getBody();
        SnakeManager.Direction direction = snake.getDirection(); // Get the snake's movement direction

        // Choose the head image based on the current direction
        Image headImage = switch (direction) {
            case UP -> headUp;
            case DOWN -> headDown;
            case LEFT -> headLeft;
            case RIGHT -> headRight;
        };

        // Draw the snake's head
        Point head = body.getFirst();
        g.drawImage(headImage, head.x * cellSize, head.y * cellSize, cellSize, cellSize, null);

        // Draw the snake's body
        for (int i = 1; i < body.size(); i++) {
            Point segment = body.get(i);
            g.drawImage(bodyImage, segment.x * cellSize, segment.y * cellSize, cellSize, cellSize, null);
        }
    }
}
