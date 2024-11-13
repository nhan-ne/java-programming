package resource.code.Model;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class FoodManager {
    private final int gridSize;
    private final List<String> foodIds;
    private final Point[] selectedFoodPositions;  // Mảng lưu trữ vị trí các món ăn đã chọn dưới dạng Point
    private final Point[] foodPositions;  // Mảng lưu trữ tất cả các vị trí món ăn dưới dạng Point
    private final FoodQDB foodQDB;

    public FoodManager(int gridSize, FoodQDB foodQDB) {
        this.gridSize = gridSize;
        this.foodQDB = foodQDB; // Khởi tạo FoodQDB
        this.foodIds = List.copyOf(foodQDB.getScoreFood().keySet());
        this.foodPositions = new Point[foodIds.size()];
        this.selectedFoodPositions = new Point[3];  // Giả sử có 3 món ăn được chọn
        update(new HashSet<>(), new HashSet<>());
    }

    // Tạo vị trí món ăn, tránh các vị trí của thân rắn và các vị trí của potion
    private void generatePosition(Set<Point> snakePositions, Set<Point> potionPositions) {
        Random rand = new Random();
        Set<Integer> usedPositions = new HashSet<>();  // Mảng lưu các vị trí đã được sử dụng

        for (int i = 0; i < foodIds.size(); i++) {
            int posX, posY;
            do {
                posX = 1 + rand.nextInt(gridSize - 2);
                posY = 1 + rand.nextInt(gridSize - 2);
            } while (usedPositions.contains(posX + posY * gridSize) || snakePositions.contains(new Point(posX, posY)) || potionPositions.contains(new Point(posX, posY)));

            foodPositions[i] = new Point(posX, posY);  // Lưu vị trí món ăn vào mảng
            usedPositions.add(posX + posY * gridSize);  // Thêm vị trí vào danh sách đã sử dụng
        }
        selectFood();  // Chọn các món ăn từ vị trí đã tạo
    }

    // Chọn món ăn từ các vị trí đã tạo
    private void selectFood() {
        Random rand = new Random();
        Set<Integer> selectedIndices = new HashSet<>();  // Tập các chỉ số món ăn đã chọn

        // Đảm bảo chỉ chọn các chỉ số khác nhau
        while (selectedIndices.size() < selectedFoodPositions.length) {
            int index = rand.nextInt(foodIds.size());  // Chọn chỉ số ngẫu nhiên
            selectedIndices.add(index);  // Thêm chỉ số vào danh sách đã chọn
        }

        // Điền các vị trí món ăn đã chọn vào mảng
        int i = 0;
        for (Integer index : selectedIndices) {
            selectedFoodPositions[i++] = foodPositions[index];
        }
    }

    // Cập nhật vị trí món ăn, tránh thân rắn và potion
    public void update(Set<Point> snakePositions, Set<Point> potionPositions) {
        generatePosition(snakePositions, potionPositions);  // Tạo món ăn tránh rắn và potion
    }

    // Trả về các vị trí món ăn đã chọn dưới dạng Set các Point
    public Set<Point> getPosition() {
        Set<Point> foodSet = new HashSet<>();
        for (Point foodPosition : selectedFoodPositions) {
            foodSet.add(foodPosition);  // Thêm mỗi vị trí món ăn đã chọn vào tập
        }
        return foodSet;
    }

    // Trả về các chỉ số của món ăn đã chọn dựa trên vị trí của chúng
    public int[] getSelectedFoodIndexes() {
        int[] selectedFoodIndexes = new int[selectedFoodPositions.length];

        for (int i = 0; i < selectedFoodPositions.length; i++) {
            // Tìm chỉ số của mỗi vị trí món ăn đã chọn trong mảng foodPositions
            selectedFoodIndexes[i] = findFoodIndex(selectedFoodPositions[i]);
        }

        return selectedFoodIndexes;
    }

    // Phương thức hỗ trợ tìm chỉ số của một vị trí món ăn
    private int findFoodIndex(Point position) {
        for (int i = 0; i < foodPositions.length; i++) {
            if (foodPositions[i].equals(position)) {
                return i;  // Trả về chỉ số của vị trí tương ứng
            }
        }
        return -1;
    }

    // Phương thức mới để lấy đường dẫn hình ảnh từ FoodQDB
    public Map<String, String> getImagePaths() {
        return foodQDB.getImagePaths(); 
    }
}