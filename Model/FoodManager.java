package resource.code.Model;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FoodManager {
    private final int[] x = new int[8]; // Mảng lưu tọa độ X của 8 vị trí thực phẩm
    private final int[] y = new int[8]; // Mảng lưu tọa độ Y của 8 vị trí thực phẩm
    private final int[] selectedFood = new int[3]; // Mảng lưu trữ chỉ số của thực phẩm được chọn
    private final int gridSize; // Kích thước lưới

    public FoodManager(int gridSize) {
        this.gridSize = gridSize; // Khởi tạo kích thước lưới
        update(new HashSet<>()); // Cập nhật vị trí thực phẩm ban đầu
    }

    private void generatePosition(Set<Point> snakeBody) {
        Random rand = new Random(); // Khởi tạo đối tượng Random để sinh số ngẫu nhiên
        Set<Integer> usedPositions = new HashSet<>(); // Tập hợp lưu trữ các vị trí đã sử dụng

        for (int i = 0; i < x.length; i++) {
            int posX, posY;
            do {
                // Sinh tọa độ ngẫu nhiên cho thực phẩm trong lưới, đảm bảo không nằm ở biên
                posX = 1 + rand.nextInt(gridSize - 2);
                posY = 1 + rand.nextInt(gridSize - 2);
            } while (usedPositions.contains(posX + posY * gridSize) || snakeBody.contains(new Point(posX, posY))); // Kiểm tra vị trí đã được sử dụng và không nằm trên cơ thể rắn
            
            x[i] = posX; // Lưu tọa độ X
            y[i] = posY; // Lưu tọa độ Y
            usedPositions.add(posX + posY * gridSize); // Thêm vào danh sách các vị trí đã sử dụng
        }
    }

    private void selectFood() {
        Random rand = new Random(); // Khởi tạo đối tượng Random để chọn thực phẩm
        Set<Integer> selectedIndices = new HashSet<>(); // Tập hợp lưu trữ các chỉ số thực phẩm đã chọn

        while (selectedIndices.size() < selectedFood.length) { // Đảm bảo chọn đủ số lượng thực phẩm
            int index = rand.nextInt(8); // Chọn ngẫu nhiên chỉ số thực phẩm từ 0 đến 7
            selectedIndices.add(index); // Thêm chỉ số vào tập hợp
        }

        int i = 0;
        for (Integer index : selectedIndices) { // Chuyển các chỉ số đã chọn vào mảng selectedFood
            selectedFood[i++] = index;
        }
    }

    public void update(Set<Point> snakeBody) {
        generatePosition(snakeBody); // Sinh các vị trí thực phẩm mới, đảm bảo không nằm trên cơ thể rắn
        selectFood(); // Chọn các thực phẩm để sử dụng
    }

    public int[][] getPosition() {
        int[][] positions = new int[3][2]; // Mảng để lưu tọa độ của thực phẩm đã chọn
        for (int i = 0; i < selectedFood.length; i++) {
            positions[i][0] = x[selectedFood[i]]; // Lưu tọa độ X
            positions[i][1] = y[selectedFood[i]]; // Lưu tọa độ Y
        }
        return positions; // Trả về tọa độ của thực phẩm đã chọn
    }

    public int[] getSelectedFood() {
        return selectedFood; // Trả về các chỉ số thực phẩm đã chọn
    }
}