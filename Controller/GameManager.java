package resource.code.Controller;

import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import resource.code.Model.FoodManager;
import resource.code.Model.GameModel;
import resource.code.Model.PotionManager;
import resource.code.Model.ScoreManager;
import resource.code.Model.Session;
import resource.code.Model.SnakeManager;
import resource.code.Model.User;
import resource.code.Model.UserQDB;
import resource.code.View.GameView;
import resource.code.View.StartGame;

public class GameManager {
    private final GameModel gameModel;
    private final FoodManager foodManager;
    private final PotionManager potionManager;
    private final SnakeManager snakeManager;
    private final ScoreManager scoreManager;
    private final GameView gameView;
    private final GameOverManager gameOverManager;
    private final CollisionManager collisionManager;
    private boolean gameStarted;
    private final User user;
    private GameLoop gameLoop;
    private boolean foodEaten = false;  // Cờ theo dõi xem thức ăn có bị ăn hay không
    private int foodEatenCount = 0;

    public GameManager(GameModel gameModel, FoodManager foodManager, PotionManager potionManager,
                       SnakeManager snakeManager, GameView gameView, ScoreManager scoreManager, User user) {
        this.gameModel = gameModel;
        this.foodManager = foodManager;
        this.potionManager = potionManager;
        this.snakeManager = snakeManager;
        this.scoreManager = scoreManager;
        this.gameView = gameView;
        this.gameOverManager = new GameOverManager();
        this.collisionManager = new CollisionManager(snakeManager, foodManager, potionManager, scoreManager, gameModel);
        this.gameStarted = false;
        this.user = user;
    }

    // Bắt đầu trò chơi mới
    public void startGame() {
        gameStarted = true;
        gameOverManager.setGameOver(false);
        snakeManager.reset(3);  // Reset rắn
        scoreManager.resetScore();  // Reset điểm số
        foodManager.update(new HashSet<>(snakeManager.getBody()), new HashSet<>());  // Cập nhật thức ăn
        potionManager.update(new HashSet<>(snakeManager.getBody()), new HashSet<>(foodManager.getPosition()));  // Cập nhật potion
        gameView.repaint();  // Vẽ lại giao diện
    }

    // Kết thúc trò chơi và xử lý game over
    public void endGame() {
        gameOverManager.setGameOver(true);
        saveHighScoreToDatabase();  // Lưu điểm cao vào cơ sở dữ liệu
        showGameOverDialog();  // Hiển thị hộp thoại game over

        if (gameLoop != null) {
            gameLoop.stopGameLoop();  // Dừng game loop nếu có
        }
    }

    private void saveHighScoreToDatabase() {
        String phone = Session.getTemporaryPhoneNumber();  // Lấy số điện thoại tạm thời từ Session
    
        // Nếu số điện thoại hợp lệ trong Session (đảm bảo rằng người dùng đã đăng nhập)
        if (phone != null) {  
            int highScore = scoreManager.getScore();  // Lấy điểm hiện tại từ game
            int currentHighScore = UserQDB.getHighScore(phone);  // Lấy điểm cao hiện tại từ cơ sở dữ liệu
    
            // Chỉ cập nhật nếu điểm hiện tại cao hơn điểm đã lưu trong cơ sở dữ liệu
            if (highScore > currentHighScore) {
                user.setHighScore(highScore);  // Cập nhật điểm cao
                UserQDB.saveHighScore(user);  // Lưu điểm cao vào cơ sở dữ liệu
            }
        } else {
            System.out.println("Chưa có số điện thoại trong Session, không thể lưu điểm.");
        }
    }
    
    

    // Hiển thị hộp thoại game over để cho phép người chơi bắt đầu lại hoặc thoát
    private void showGameOverDialog() {
        int score = scoreManager.getScore();  // Lấy điểm số hiện tại
        int choice = JOptionPane.showOptionDialog(
            null,
            "Game Over! Bạn có muốn chơi lại không?\nĐiểm của bạn: " + score,
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            null,
            null
        );

        if (choice == JOptionPane.YES_OPTION) {
            startGame();  // Bắt đầu lại trò chơi
        } else {
            closeGameWindow();  // Đóng cửa sổ trò chơi
            openStartGameWindow();  // Mở cửa sổ trò chơi bắt đầu
        }
    }

    // Đóng cửa sổ trò chơi
    private void closeGameWindow() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gameView);
        if (frame != null) {
            frame.dispose();  // Hủy cửa sổ trò chơi
        }
    }

    // Mở cửa sổ trò chơi bắt đầu
    private void openStartGameWindow() {
        SwingUtilities.invokeLater(StartGame::new);  // Mở cửa sổ trò chơi bắt đầu
    }

    // Cập nhật trạng thái trò chơi mỗi khung hình
    public void update() {
        if (gameStarted && !gameOverManager.isGameOver()) {
            snakeManager.move();
            checkGameOver();

            // Kiểm tra va chạm với thức ăn và potion
            if (collisionManager.checkFoodCollision()) {
                foodManager.update(new HashSet<>(snakeManager.getBody()), new HashSet<>());
                foodEaten = true;  // Rắn ăn thức ăn
                foodEatenCount++;  // Tăng số lượng thức ăn đã ăn
                
                // Nếu đã ăn đủ 3 thức ăn, hiển thị potion
                if (foodEatenCount >= 5) {
                    // Kiểm tra nếu potion không được ăn
                    if (!collisionManager.checkPotionCollision()) {
                        potionManager.removePotion();  // Xóa potion
                        potionManager.update(new HashSet<>(snakeManager.getBody()), new HashSet<>(foodManager.getPosition()));
                    } 
                    foodEatenCount = 0;  // Reset đếm thức ăn sau khi ăn đủ 3 món
                }
                increaseGameSpeed();  // Tăng tốc độ trò chơi
            }
            if(collisionManager.checkPotionCollision()){
                foodEatenCount = 0;
            }
        }
    }


    // Phương thức tăng tốc độ trò chơi sau khi ăn thức ăn
    private void increaseGameSpeed() {
        if (foodEaten) {
            snakeManager.increaseSpeed();  // Tăng tốc độ của rắn
            foodEaten = false;  // Reset cờ thức ăn đã ăn
        }
    }

    // Kiểm tra va chạm game over
    private void checkGameOver() {
        if (collisionManager.checkGameOver(gameModel.getRows(), gameModel.getCols())) {
            endGame();  // Nếu va chạm với chính mình, kết thúc trò chơi
        }
    }
}
