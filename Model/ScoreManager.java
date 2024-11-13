package resource.code.Model;

public class ScoreManager {
    private int score;
    private final FoodQDB foodQDB;

    public ScoreManager(FoodQDB foodQDB) {
        this.score = 0;
        this.foodQDB = foodQDB;
    }

    public int getScore() {
        return score;
    }
    // Thêm điểm cho mỗi thức ăn
    public void addScoreForFood(int foodIndex) {
        // Sử dụng ID thực phẩm để lấy điểm
        String foodId = foodQDB.getScoreFood().keySet().toArray(new String[0])[foodIndex];
        Integer foodScore = foodQDB.getScoreFood().get(foodId);

        if (foodScore != null) {
            score += foodScore;
        }
    }
    public void potionX2Score() {
        score *= 2;
    }

    public void resetScore() {
        this.score = 0;
    }
}
