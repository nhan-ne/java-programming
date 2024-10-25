package resource.code.Model;

import java.awt.Point;

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

    public void checkGameOver(SnakeManager snakeManager, int rows, int cols) {
        Point head = snakeManager.getHead();
        if (head.x < 0 || head.x >= cols || head.y < 0 || head.y >= rows) {
            setGameOver(true);
        }
        if (snakeManager.checkSelfCollision()) {
            setGameOver(true);
        }
    }
}