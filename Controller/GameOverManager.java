package resource.code.Controller;

import resource.code.Model.FoodManager;
import resource.code.Model.GameModel;
import resource.code.Model.PotionManager;
import resource.code.Model.ScoreManager;
import resource.code.Model.SnakeManager;

public class GameOverManager {
    private boolean gameOver;

    public GameOverManager() {
        this.gameOver = false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkGameOver(SnakeManager snakeManager, FoodManager foodManager, PotionManager potionManager, ScoreManager scoreManager, GameModel gameModel) {
        // Kiểm tra nếu game kết thúc do va chạm với biên hoặc tự cắn
        CollisionManager collisionManager = new CollisionManager(snakeManager, foodManager, potionManager, scoreManager, gameModel);
        if (collisionManager.checkGameOver(gameModel.getRows(), gameModel.getCols())) {
            setGameOver(true);
        }
    }
}
