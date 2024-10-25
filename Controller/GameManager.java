package resource.code.Controller;

import java.util.HashSet;

import javax.swing.JOptionPane;

import resource.code.Model.FoodManager;
import resource.code.Model.GameModel;
import resource.code.Model.GameOverManager;
import resource.code.Model.ScoreManager;
import resource.code.Model.SnakeManager;
import resource.code.View.GameView;

public class GameManager {
    private final GameModel gameModel;
    private final FoodManager foodManager;
    private final SnakeManager snakeManager;
    private final ScoreManager scoreManager;
    private final GameView gameView;
    private final GameOverManager gameOverManager;
    private boolean gameStarted; // Track if the game has started

    public GameManager(GameModel gameModel, FoodManager foodManager, SnakeManager snakeManager, GameView gameView, ScoreManager scoreManager) {
        this.gameModel = gameModel;
        this.foodManager = foodManager;
        this.snakeManager = snakeManager;
        this.scoreManager = scoreManager;
        this.gameView = gameView;
        this.gameOverManager = new GameOverManager();
        this.gameStarted = false; // Initialize as false
    }

    public void startGame() {
        gameStarted = true; // Set the game as started
        gameOverManager.setGameOver(false);
        snakeManager.reset();
        scoreManager.resetScore();
        foodManager.update(new HashSet<>(snakeManager.getBody())); 
        gameView.repaint(); 
    }

    public void endGame() {
        gameOverManager.setGameOver(true);
        showGameOverDialog();
    }

    private void showGameOverDialog() {
        int choice = JOptionPane.showOptionDialog(
                null,
                "Game Over! Would you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                null
        );

        if (choice == JOptionPane.YES_OPTION) {
            startGame(); 
        } else {
            System.exit(0); 
        }
    }

    public void update() {
        if (!gameOverManager.isGameOver() && gameStarted) { // Check if the game has started
            snakeManager.move(); 
            checkGameOver(); 

            // Check if the snake eats any of the food items
            int[][] foodPositions = foodManager.getPosition();
            for (int[] foodPosition : foodPositions) {
                if (snakeManager.checkFoodCollision(foodPosition)) {
                    snakeManager.grow(); 
                    scoreManager.increaseScore(10); //If the snake eats, the score increases by 10
                    foodManager.update(new HashSet<>(snakeManager.getBody())); 
                    break; // Exit the loop after eating one food
                }
            }
        }
    }

    public void checkGameOver() {
        gameOverManager.checkGameOver(snakeManager, gameModel.getRows(), gameModel.getCols());
        if (gameOverManager.isGameOver()) {
            endGame();
        }
    }
}