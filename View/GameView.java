package resource.code.View;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import resource.code.Model.FoodManager;
import resource.code.Model.GameModel;
import resource.code.Model.PotionManager;
import resource.code.Model.ScoreManager;
import resource.code.Model.SnakeManager;

public class GameView extends JPanel {
    private final MapRenderer mapRenderer;
    private final FoodRenderer foodRenderer;
    private final PotionRenderer potionRenderer;
    private final SnakeRenderer snakeRenderer;
    private final ScoreRenderer scoreRenderer;
    private final int cellSize;
    
    public GameView(GameModel model, FoodManager foodModel, PotionManager potionModel, SnakeManager snakeModel, ScoreManager scoreManager) {
        this.cellSize = 30;

        // Khởi tạo các renderer với cellSize
        this.mapRenderer = new MapRenderer(model, cellSize);
        this.foodRenderer = new FoodRenderer(foodModel, cellSize);
        this.potionRenderer = new PotionRenderer(potionModel, cellSize);  // Khởi tạo PotionRenderer
        this.snakeRenderer = new SnakeRenderer(snakeModel, cellSize);
        this.scoreRenderer = new ScoreRenderer(scoreManager);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ bản đồ
        mapRenderer.render(g);

        // Vẽ thức ăn
        foodRenderer.render(g);

        // Vẽ potion
        potionRenderer.render(g);  // Vẽ potion

        // Vẽ rắn
        snakeRenderer.render(g);

        // Vẽ điểm số
        scoreRenderer.render(g);
    }

    @Override
    public Dimension getPreferredSize() {
        // Điều chỉnh kích thước dựa trên kích thước bản đồ (và đảm bảo các potion vừa vặn)
        return new Dimension(mapRenderer.getCols() * cellSize, mapRenderer.getRows() * cellSize); 
    }
}
