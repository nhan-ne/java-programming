package resource.code.Model;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class PotionManager {
    private final int gridSize;
    private final List<String> potionIds;  // Danh sách các ID potion
    private int selectedPotion;  // ID của potion đã chọn
    private Point potionPosition;  // Vị trí của potion đã chọn

    public PotionManager(int gridSize, PotionQDB potionQDB) {
        this.gridSize = gridSize;
        this.potionIds = potionQDB.getPotionIds();  // Lấy danh sách các potion ID từ cơ sở dữ liệu
        this.selectedPotion = -1;  // Không chọn potion nào ban đầu
        this.potionPosition = new Point(-1, -1);  // Đặt vị trí potion bên ngoài bản đồ ban đầu
        update(new HashSet<>(), new HashSet<>());  // Cập nhật vị trí của potion, tránh các vị trí của rắn và thực phẩm
    }

    // Tạo vị trí ngẫu nhiên cho potion, tránh các vị trí của rắn và thực phẩm
    private void generatePosition(Set<Point> snakePositions, Set<Point> foodPositions) {
        Random rand = new Random();
        selectedPotion = rand.nextInt(potionIds.size());  // Chọn một potion ngẫu nhiên

        int posX, posY;
        do {
            posX = 1 + rand.nextInt(gridSize - 2);  // Tạo tọa độ x ngẫu nhiên trong phạm vi bản đồ
            posY = 1 + rand.nextInt(gridSize - 2);  // Tạo tọa độ y ngẫu nhiên trong phạm vi bản đồ
        } while (snakePositions.contains(new Point(posX, posY)) || foodPositions.contains(new Point(posX, posY)));

        potionPosition.setLocation(posX, posY);  // Đặt vị trí mới cho potion
    }

    // Cập nhật vị trí potion khi bản đồ được làm mới
    public void update(Set<Point> snakePositions, Set<Point> foodPositions) {
        // Nếu potion nằm ngoài bản đồ, tạo vị trí hợp lệ mới
        if (potionPosition.equals(new Point(-1, -1))) {
            generatePosition(snakePositions, foodPositions);  // Cập nhật vị trí potion
        }
    }

    // Trả về vị trí của potion dưới dạng Set chứa Point
    public Set<Point> getPotionPosition() {
        Set<Point> potionSet = new HashSet<>();
        if (!potionPosition.equals(new Point(-1, -1))) {
            potionSet.add(potionPosition);  // Thêm vị trí potion vào set nếu hợp lệ
        }
        return potionSet;  // Trả về set chứa vị trí của potion (có thể rỗng nếu potionPosition nằm ngoài bản đồ)
    }

    // Trả về ID của potion đã chọn
    public String getSelectedPotionId() {
        return potionIds.get(selectedPotion);  // Trả về ID của potion đã chọn
    }

    // Trả về chỉ số của potion đã chọn trong danh sách
    public int getSelectedPotionIndex() {
        return selectedPotion;  // Trả về chỉ số của potion đã chọn
    }

    // Kiểm tra xem vị trí cho trước có trùng với vị trí của potion không
    public String getPotionIdAt(Point position) {
        if (potionPosition.equals(position)) {
            return getSelectedPotionId();  // Trả về ID của potion nếu vị trí trùng khớp
        }
        return null;  // Trả về null nếu không có sự khớp hoặc potionPosition không hợp lệ
    }

    // Loại bỏ potion khỏi bản đồ sau khi được tiêu thụ, đặt nó ra ngoài bản đồ
    public void removePotion() {
        potionPosition.setLocation(-1, -1);  // Đặt vị trí của potion ra ngoài bản đồ (vị trí không hợp lệ)
    }

    // Lấy đường dẫn hình ảnh của potion đã chọn từ PotionQDB
    public String getSelectedPotionImagePath(PotionQDB potionQDB) {
        String potionId = getSelectedPotionId();  // Lấy ID của potion đã chọn
        return potionQDB.getImagePaths().get(potionId);  // Trả về đường dẫn hình ảnh của potion
    }
}
