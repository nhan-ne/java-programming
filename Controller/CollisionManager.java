package resource.code.Controller;

import java.awt.Point;

import resource.code.Model.FoodManager;
import resource.code.Model.GameModel;
import resource.code.Model.PotionManager;
import resource.code.Model.ScoreManager;
import resource.code.Model.SnakeManager;

public class CollisionManager {
    private final SnakeManager snakeManager;
    private final FoodManager foodManager;
    private final PotionManager potionManager;
    private final ScoreManager scoreManager;
    private final GameModel gameModel;

    public CollisionManager(SnakeManager snakeManager, FoodManager foodManager, PotionManager potionManager, ScoreManager scoreManager, GameModel gameModel) {
        this.snakeManager = snakeManager;
        this.foodManager = foodManager;
        this.potionManager = potionManager;
        this.scoreManager = scoreManager;
        this.gameModel = gameModel;
    }

    public boolean checkFoodAndPotionCollisions() {
        Point head = snakeManager.getHead();

        // Kiểm tra va chạm với food
        for (Point foodPosition : foodManager.getPosition()) {
            if (head.equals(foodPosition)) {
                int[] foodIndexes = foodManager.getSelectedFoodIndexes();
                for (int foodIndex : foodIndexes) {
                    if (foodManager.getPosition().contains(foodPosition)) {
                        scoreManager.addScoreForFood(foodIndex);
                    }
                }
                snakeManager.grow(0);  // Rắn lớn lên sau khi ăn thức ăn
                return true;  // Ăn thức ăn thành công
            }
        }

        // Kiểm tra va chạm với Potion
        for (Point potionPosition : potionManager.getPotionPosition()) {
            if (head.equals(potionPosition)) {
                String potionId = potionManager.getPotionIdAt(potionPosition);
                applyPotionEffect(potionId);
                return true;  // Potion ăn thành công
            }
        }

        return false;  // Không có va chạm nào
    }

    private void applyPotionEffect(String potionId) {
        switch (potionId) {
            case "P1":
                scoreManager.potionX2Score();
                break;
            case "P2":
                snakeManager.setSpeed(10);  // Tăng tốc độ của rắn
                break;
            case "P3":
                snakeManager.grow(3);  // Rắn lớn lên thêm 3 phần
                break;
            default:
                break;  // Nếu không có hiệu ứng, không làm gì
        }
        potionManager.removePotion();
    }

    // Kiểm tra nếu rắn va chạm với biên hoặc tự cắn
    public boolean checkGameOver(int rows, int cols) {
        Point head = snakeManager.getHead();

        // Kiểm tra va chạm với biên bằng cách gọi phương thức isBorder từ GameModel
        if (gameModel.isBorder(head.x, head.y)) {
            return true; // Game over nếu ra ngoài biên
        }

        // Kiểm tra va chạm với chính rắn (tự cắn)
        for (int i = 1; i < snakeManager.getBody().size(); i++) {  // Bỏ qua đầu rắn, kiểm tra thân
            if (snakeManager.getBody().get(i).equals(head)) {
                return true; // Game over nếu đầu va chạm với thân
            }
        }

        return false; // Không có va chạm, tiếp tục trò chơi
    }

}
