package resource.code.View;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import resource.code.Model.FoodManager;
import resource.code.Model.GameModel;
import resource.code.Model.ScoreManager;
import resource.code.Model.SnakeManager;

public class GameView extends JPanel {
    private final MapRenderer mapRenderer;
    private final FoodRenderer foodRenderer;
    private final SnakeRenderer snakeRenderer;
    private final ScoreRenderer scoreRenderer; 
    private final int cellSize;

    public GameView(GameModel model, FoodManager foodModel, SnakeManager snakeModel, ScoreManager scoreManager) {
        this.cellSize = 30;

        // Khởi tạo renderers với cellSize
        this.mapRenderer = new MapRenderer(model, cellSize);
        this.foodRenderer = new FoodRenderer(foodModel, cellSize);
        this.snakeRenderer = new SnakeRenderer(snakeModel, cellSize);
        this.scoreRenderer = new ScoreRenderer(scoreManager);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the map
        mapRenderer.render(g);

        // Render the food
        foodRenderer.render(g);

        // Render the snake
        snakeRenderer.render(g);

        // Render the score
        scoreRenderer.render(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(mapRenderer.getCols() * cellSize, mapRenderer.getRows() * cellSize); // Kích thước bảng
    }
}
