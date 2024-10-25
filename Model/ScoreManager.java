// File: ScoreManager.java
package resource.code.Model;

public class ScoreManager {
    private int score; // Biến lưu trữ điểm số hiện tại

    public ScoreManager() {
        this.score = 0; // Khởi tạo điểm số là 0
    }

    // Phương thức tăng điểm
    public void increaseScore(int points) {
        this.score += points;
    }

    // Phương thức thiết lập điểm
    public void setScore(int score) {
        this.score = score;
    }

    // Phương thức lấy điểm số hiện tại
    public int getScore() {
        return this.score;
    }

    // Phương thức đặt lại điểm số
    public void resetScore() {
        this.score = 0;
    }
}
