package resource.code.Controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import resource.code.Model.FoodManager;
import resource.code.Model.GameModel;
import resource.code.Model.SnakeManager;
import resource.code.View.GameView;
import resource.code.View.StartGame; // Import the new StartGame class

public class GameController {
    private final GameModel model;
    private final FoodManager foodModel;
    private final SnakeManager snakeModel;
    private final GameView view;
    private final GameManager gameManager;
    private final KeyManager keyManager;

    public GameController() {
        model = new GameModel(20, 20);
        foodModel = new FoodManager(20);
        snakeModel = new SnakeManager(5, 5, SnakeManager.Direction.RIGHT);
        
        view = new GameView(model, foodModel, snakeModel);
        gameManager = new GameManager(model, foodModel, snakeModel, view);
        keyManager = new KeyManager(snakeModel);

        // Set up JFrame
        JFrame frame = new JFrame("Snake");
        frame.add(view);
        frame.addKeyListener(keyManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the game
        gameManager.startGame();
        startGameLoop();
    }

    private void startGameLoop() {
        new Thread(() -> {
            while (true) {
                gameManager.update();
                view.repaint();
                try {
                    Thread.sleep(100); // Control game speed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        // Show the start game screen first
        SwingUtilities.invokeLater(() -> {
            StartGame startGame = new StartGame();
            startGame.setVisible(true);
        });
    }
}