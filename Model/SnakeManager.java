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
    private boolean hasStarted;  // Trạng thái bắt đầu game
    private int speed; // Tốc độ di chuyển của rắn

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Constructor
    public SnakeManager(int startX, int startY, Direction startDirection, int initialLength) {
        this.startX = startX;
        this.startY = startY;
        this.startDirection = startDirection;
        body = new LinkedList<>();
        reset(initialLength);  // Sử dụng tham số này để thay đổi chiều dài ban đầu
    }

    // Phương thức reset lại rắn, tạo độ dài ban đầu cho rắn
    public void reset(int initialLength) {
        body.clear();
        for (int i = 0; i < initialLength; i++) {
            body.add(new Point(startX - i, startY));  // Tạo các đoạn thân rắn ở vị trí ban đầu
        }

        direction = startDirection;
        grow = false;
        hasStarted = false;
        speed = 150;  // Tốc độ ban đầu
    }

    // Phương thức di chuyển rắn
    public void move() {
        if (!hasStarted) return;  // Nếu game chưa bắt đầu, không di chuyển

        Point head = getHead();
        Point newHead = new Point(head);

        // Nếu di chuyển, giảm tọa độ theo hướng
        switch (direction) {
            case UP -> newHead.translate(0, -1);
            case DOWN -> newHead.translate(0, 1);
            case LEFT -> newHead.translate(-1, 0);
            case RIGHT -> newHead.translate(1, 0);
        }

        body.addFirst(newHead); // Thêm đoạn mới vào đầu
        if (!grow) {
            body.removeLast(); // Loại bỏ đoạn cuối nếu không cần grow
        } else {
            grow = false; // Reset lại biến grow sau khi rắn phát triển
        }
    }

    // Phương thức bắt đầu di chuyển rắn
    public void startMoving() {
        hasStarted = true;  // Cập nhật trạng thái khi game bắt đầu
    }

    // Phương thức để rắn phát triển thêm
public void grow(int segments) {
    // Lấy điểm cuối của rắn (đoạn thân cuối cùng)
    Point lastSegment = body.getLast();
    
    // Thêm từng đoạn mới vào cuối cùng theo hướng ngược lại với đầu
    for (int i = 0; i < segments; i++) {
        // Thêm một đoạn thân mới vào cuối cùng của rắn, vị trí được tính từ cuối
        Point newSegment = new Point(lastSegment);
        body.addLast(newSegment);
    }

    grow = true;  // Đánh dấu rằng rắn cần phát triển
}


    // Phương thức thay đổi hướng của rắn
    public void setDirection(Direction newDirection) {
        // Kiểm tra không cho phép quay ngược lại hướng đối diện
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
            (direction == Direction.DOWN && newDirection != Direction.UP) ||
            (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            direction = newDirection;
        }
    }

    // Lấy đầu của rắn
    public Point getHead() {
        return body.getFirst();
    }

    // Lấy toàn bộ cơ thể rắn
    public LinkedList<Point> getBody() {
        return body;
    }

    // Lấy hướng hiện tại của rắn
    public Direction getDirection() {
        return direction;
    }

    // Getter và Setter cho speed
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(50, Math.min(250, this.speed + speed));  // Điều chỉnh tốc độ trong khoảng từ 50 đến 250
    }

    // Phương thức tăng tốc độ di chuyển của rắn
    public void increaseSpeed() {
        this.speed = Math.max(50, this.speed - 5);  // Tăng tốc độ (giảm giá trị speed)
    }
}
