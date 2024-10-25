package resource.code.Model;

import java.awt.Point;
import java.util.LinkedList;

public final class SnakeManager {
    private final LinkedList<Point> body; // Danh sách các điểm tạo thành cơ thể rắn
    private Direction direction; // Hướng di chuyển của rắn
    private boolean grow; // Biến đánh dấu xem rắn có cần lớn thêm không
    private final int startX; // Tọa độ X khởi đầu
    private final int startY; // Tọa độ Y khởi đầu
    private final Direction startDirection; // Hướng khởi đầu

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public SnakeManager(int startX, int startY, Direction startDirection) {
        this.startX = startX; // Khởi tạo tọa độ X
        this.startY = startY; // Khởi tạo tọa độ Y
        this.startDirection = startDirection; // Khởi tạo hướng
        body = new LinkedList<>(); // Khởi tạo danh sách cơ thể rắn
        reset(); // Đặt lại trạng thái ban đầu cho rắn
    }

    public void reset() {
        body.clear(); // Xóa danh sách cơ thể rắn
        body.add(new Point(startX, startY)); // Thêm đầu rắn tại vị trí khởi đầu
        direction = startDirection; // Đặt hướng khởi đầu
        grow = false; // Đặt biến grow về false
    }

    public void move() {
        Point head = getHead(); // Lấy vị trí đầu rắn
        Point newHead = new Point(head); // Tạo một điểm mới cho đầu rắn

        // Cập nhật vị trí đầu rắn dựa trên hướng hiện tại
        switch (direction) {
            case UP -> newHead.translate(0, -1);
            case DOWN -> newHead.translate(0, 1);
            case LEFT -> newHead.translate(-1, 0);
            case RIGHT -> newHead.translate(1, 0);
        }

        body.addFirst(newHead); // Thêm đầu rắn mới vào danh sách
        if (!grow) {
            body.removeLast(); // Nếu không lớn thêm thì xóa đuôi
        } else {
            grow = false; // Reset biến grow
        }
    }

    public void grow() {
        grow = true; // Đặt cờ cho rắn lớn thêm
    }

    public boolean checkFoodCollision(int[] foodPosition) {
        Point head = getHead(); // Lấy vị trí đầu rắn
        return head.x == foodPosition[0] && head.y == foodPosition[1]; // Kiểm tra va chạm với thực phẩm
    }

    public void setDirection(Direction newDirection) {
        // Ngăn chặn rắn quay ngược lại
        if (direction == Direction.UP && newDirection != Direction.DOWN) {
            direction = newDirection;
        } else if (direction == Direction.DOWN && newDirection != Direction.UP) {
            direction = newDirection;
        } else if (direction == Direction.LEFT && newDirection != Direction.RIGHT) {
            direction = newDirection;
        } else if (direction == Direction.RIGHT && newDirection != Direction.LEFT) {
            direction = newDirection;
        }
    }

    public Point getHead() {
        return body.getFirst(); // Trả về vị trí của đầu rắn
    }

    public LinkedList<Point> getBody() {
        return body; // Cung cấp quyền truy cập vào cơ thể rắn
    }

    public Direction getDirection() {
        return direction; // Cung cấp quyền truy cập vào hướng hiện tại
    }

    public boolean checkSelfCollision() {
        Point head = getHead(); // Lấy vị trí đầu rắn
        for (int i = 1; i < body.size(); i++) { // Kiểm tra va chạm với chính mình
            if (head.equals(body.get(i))) {
                return true; // Va chạm với chính mình
            }
        }
        return false; // Không có va chạm
    }
}