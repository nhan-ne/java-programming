package resource.code.Controller;

import javax.swing.SwingUtilities;

import resource.code.Model.SnakeManager;
import resource.code.View.GameView;

public class GameLoop implements Runnable {
    private final GameManager gameManager;
    private final GameView view;
    private final SnakeManager snakeManager;  // Thêm tham chiếu đến SnakeManager
    private volatile boolean running;
    private Thread gameThread;  // Biến để lưu trữ thread trò chơi

    public GameLoop(GameManager gameManager, GameView view, SnakeManager snakeManager) {
        this.gameManager = gameManager;
        this.view = view;
        this.snakeManager = snakeManager;  // Lưu tham chiếu SnakeManager
        this.running = false;  // Ban đầu trò chơi chưa chạy
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (gameManager != null) {
                gameManager.update();  // Cập nhật trạng thái trò chơi

                SwingUtilities.invokeLater(() -> view.repaint());  // Cập nhật giao diện

            } else {
                System.err.println("GameManager là null trong vòng lặp trò chơi. Dừng vòng lặp.");
                stopGameLoop();  // Dừng vòng lặp một cách an toàn
                break;
            }

            try {
                // Sử dụng tốc độ động của rắn từ SnakeManager
                int gameSpeed = snakeManager.getSpeed();  // Lấy tốc độ của rắn
                Thread.sleep(gameSpeed);  // Điều chỉnh tốc độ động dựa trên tốc độ của rắn
            } catch (InterruptedException e) {
                System.err.println("Vòng lặp trò chơi bị gián đoạn. Dừng lại.");
                Thread.currentThread().interrupt();
                stopGameLoop();  // Dừng vòng lặp trò chơi nếu bị gián đoạn
                break;
            }
        }
    }

    // Phương thức để bắt đầu vòng lặp trò chơi
    public void startGameLoop() {
        if (gameThread == null || !gameThread.isAlive()) {
            gameThread = new Thread(this);  // Khởi tạo thread mới
            gameThread.start();  // Bắt đầu vòng lặp trò chơi
        }
    }

    // Phương thức để dừng vòng lặp trò chơi
    public void stopGameLoop() {
        running = false;
        if (gameThread != null) {
            try {
                gameThread.join();  // Chờ thread kết thúc
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }
}
