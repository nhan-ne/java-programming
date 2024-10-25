package resource.code.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import resource.code.Model.ScoreManager;

public class ScoreRenderer {
    private final ScoreManager scoreManager;

    public ScoreRenderer(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    // Phương thức render để vẽ điểm số
    public void render(Graphics g) {
        g.setColor(Color.WHITE); // Màu chữ là trắng
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Font chữ
        g.drawString("Score: " + scoreManager.getScore(), 10, 20); // Vẽ điểm số tại tọa độ (10, 20)
    }
}
