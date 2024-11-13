package resource.code.Controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import resource.code.Model.FoodManager;
import resource.code.Model.FoodQDB;
import resource.code.Model.GameModel;
import resource.code.Model.PotionManager;
import resource.code.Model.PotionQDB;
import resource.code.Model.ScoreManager;
import resource.code.Model.Session;
import resource.code.Model.SnakeManager;
import resource.code.Model.User;
import resource.code.View.GameView;
import resource.code.View.StartGame;

public class Launcher {
    private final GameModel model;
    private final FoodManager foodModel;
    private final PotionManager potionModel;
    private final SnakeManager snakeModel;
    private final ScoreManager scoreManager;
    private final FoodQDB foodQDB;
    private final PotionQDB potionQDB;
    private final GameView view;
    private final GameManager gameManager;
    private final User user;
    private final CollisionManager collisionManager; // Khai báo collisionManager

    // Khởi tạo Launcher với số điện thoại của người dùng
    public Launcher(String phoneNumber) {
        model = new GameModel(20, 20); // Kích thước lưới
        foodQDB = new FoodQDB();
        foodModel = new FoodManager(20, foodQDB); // Số lượng món ăn
        potionQDB = new PotionQDB();
        potionModel = new PotionManager(20, potionQDB);
        snakeModel = new SnakeManager(5, 5, SnakeManager.Direction.RIGHT, 3); // Vị trí ban đầu của rắn
        scoreManager = new ScoreManager(foodQDB);
        user = new User(phoneNumber, 0); // Người dùng với số điện thoại và điểm số
        collisionManager = new CollisionManager(snakeModel, foodModel, potionModel,scoreManager,model); // Khởi tạo collisionManager

        // Khởi tạo view và game manager
        view = new GameView(model, foodModel, potionModel, snakeModel, scoreManager);
        gameManager = new GameManager(model, foodModel, potionModel, snakeModel, view, scoreManager, user);

        setupFrame();  // Thiết lập cửa sổ trò chơi
        startGame();  // Bắt đầu trò chơi
    }

    // Thiết lập cửa sổ trò chơi
    private void setupFrame() {
        JFrame frame = new JFrame("Snake");
        frame.add(view);  // Thêm panel GameView vào cửa sổ
        frame.addKeyListener(new KeyManager(snakeModel, model, collisionManager));  // Sử dụng collisionManager đã khai báo
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Đóng cửa sổ khi kết thúc trò chơi
        frame.pack();
        frame.setLocationRelativeTo(null);  // Đặt cửa sổ ở giữa màn hình
        frame.setResizable(false);  // Không cho phép thay đổi kích thước cửa sổ
        frame.setVisible(true);  // Hiển thị cửa sổ
    }

    // Bắt đầu trò chơi
    private void startGame() {
        if (gameManager != null) {
            gameManager.startGame();
            startGameLoop();  // Bắt đầu vòng lặp trò chơi
        } else {
            System.err.println("GameManager chưa được khởi tạo!");
        }
    }

    // Bắt đầu vòng lặp trò chơi trong một thread mới
    private void startGameLoop() {
        if (gameManager != null) {
            new Thread(new GameLoop(gameManager, view, snakeModel)).start();  // Khởi động vòng lặp trò chơi trong một thread mới
        } else {
            System.err.println("GameManager là null, không thể bắt đầu vòng lặp trò chơi!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String phoneNumber = Session.getTemporaryPhoneNumber();  // Lấy số điện thoại tạm thời
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                new StartGame();  // Hiển thị màn hình bắt đầu nếu không có số điện thoại
            } else {
                new Launcher(phoneNumber);  // Bắt đầu trò chơi với số điện thoại hiện tại
            }
        });
    }
}
