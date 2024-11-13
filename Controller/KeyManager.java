package resource.code.Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import resource.code.Model.GameModel;
import resource.code.Model.SnakeManager;

public class KeyManager extends KeyAdapter {
    private final SnakeManager snakeManager;
    private final GameModel gameModel;  // Adding GameModel instance
    private final CollisionManager collisionManager;  // Adding CollisionManager instance

    public KeyManager(SnakeManager snakeManager, GameModel gameModel, CollisionManager collisionManager) {
        this.snakeManager = snakeManager;
        this.gameModel = gameModel;
        this.collisionManager = collisionManager;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Kiểm tra nếu không phải game over
        if (!collisionManager.checkGameOver(gameModel.getRows(), gameModel.getCols())) {
            // Bắt đầu di chuyển khi người chơi nhấn phím
            snakeManager.startMoving();

            // Điều khiển di chuyển của rắn
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    snakeManager.setDirection(SnakeManager.Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    snakeManager.setDirection(SnakeManager.Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    snakeManager.setDirection(SnakeManager.Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    snakeManager.setDirection(SnakeManager.Direction.RIGHT);
                    break;
            }
        }
    }
}
