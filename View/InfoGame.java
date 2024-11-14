package resource.code.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import resource.code.Model.FoodManager;
import resource.code.Model.FoodQDB;
import resource.code.Model.PotionManager;
import resource.code.Model.PotionQDB;

public class InfoGame {
    private final FoodQDB foodQDB;
    private final PotionQDB potionQDB;

    // Constructor nhận vào FoodManager và PotionManager
    public InfoGame(FoodManager foodManager, PotionManager potionManager) {
        this.foodQDB = new FoodQDB();  // Tạo đối tượng FoodQDB để lấy thông tin món ăn
        this.potionQDB = new PotionQDB();  // Tạo đối tượng PotionQDB để lấy thông tin thuốc
    }

    // Phương thức hiển thị thông tin món ăn (ID, điểm số và hình ảnh)
    public void renderFoodInfo(Graphics g, int x, int y) {
        Map<String, String> imagePaths = foodQDB.getImagePaths();  // Lấy đường dẫn hình ảnh món ăn
        Map<String, Integer> foodScores = foodQDB.getScoreFood();  // Lấy điểm số món ăn

        // Tiêu đề cho thông tin món ăn
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));  // Đặt font cho tiêu đề
        g.drawString("Food Info:", x, y);

        g.setFont(new Font("Arial", Font.PLAIN, 14));  // Đặt font cho các mục món ăn
        Set<String> foodIds = foodScores.keySet();
        int offsetY = 20;  // Khoảng cách dọc khi hiển thị thông tin món ăn

        // Lặp qua tất cả các món ăn để hiển thị thông tin
        for (String foodId : foodIds) {
            // Lấy thông tin chi tiết món ăn theo ID
            String foodInfo = foodQDB.getFoodInfoById(foodId);

            // Hiển thị hình ảnh món ăn (nếu có)
            String imagePath = imagePaths.get(foodId);
            BufferedImage foodImage = loadImage(imagePath);  // Tải hình ảnh từ đường dẫn
            if (foodImage != null) {
                // Thay đổi kích thước hình ảnh cho nhỏ hơn, điều chỉnh chiều rộng và chiều cao
                g.drawImage(foodImage, x, y + offsetY - 15, 30, 30, null);  // Thay đổi kích thước thành 30x30
            }

            // Hiển thị thông tin món ăn (ID, tên, điểm số) bên cạnh hình ảnh
            g.drawString(foodInfo, x + 40, y + offsetY);  // Vị trí văn bản bên cạnh hình ảnh
            offsetY += 40;  // Điều chỉnh khoảng cách dọc cho mục tiếp theo
        }
    }

    // Phương thức hiển thị thông tin thuốc (hình ảnh, tên và mô tả)
    public void renderPotionInfo(Graphics g, int x, int y) {
        Map<String, String> potionInfos = potionQDB.getPotionInfos();  // Lấy thông tin thuốc
        Map<String, String> potionImagePaths = potionQDB.getImagePaths();  // Lấy đường dẫn hình ảnh thuốc
        Map<String, String> potionNames = potionQDB.getPotionNames();  // Lấy tên thuốc

        // Tiêu đề cho thông tin thuốc
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));  // Đặt font cho tiêu đề
        g.drawString("Potion Info:", x, y);

        g.setFont(new Font("Arial", Font.PLAIN, 14));  // Đặt font cho các mục thuốc
        Set<String> potionIds = potionInfos.keySet();
        int offsetY = 20;  // Khoảng cách dọc khi hiển thị thông tin thuốc

        // Lặp qua tất cả các thuốc để hiển thị thông tin
        for (String potionId : potionIds) {
            String potionName = potionNames.get(potionId);  // Lấy tên thuốc
            String info = potionInfos.get(potionId);  // Lấy thông tin thuốc

            // Hiển thị hình ảnh thuốc (nếu có)
            String potionImagePath = potionImagePaths.get(potionId);
            BufferedImage potionImage = loadImage(potionImagePath);  // Tải hình ảnh từ đường dẫn
            if (potionImage != null) {
                // Thay đổi kích thước hình ảnh cho nhỏ hơn, điều chỉnh chiều rộng và chiều cao
                g.drawImage(potionImage, x, y + offsetY - 15, 30, 30, null);  // Thay đổi kích thước thành 30x30
            }

            // Hiển thị tên thuốc và thông tin bên cạnh hình ảnh
            g.drawString(potionName + ": " + info, x + 40, y + offsetY);  // Vị trí văn bản bên cạnh hình ảnh
            offsetY += 40;  // Điều chỉnh khoảng cách dọc cho mục tiếp theo
        }
    }

    // Phương thức tải hình ảnh từ đường dẫn tệp
    private BufferedImage loadImage(String imagePath) {
        try {
            // Kiểm tra xem đường dẫn hình ảnh có hợp lệ và tải hình ảnh
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // Trả về null nếu không thể tải hình ảnh
        }
    }

    // Phương thức hiển thị cửa sổ thông tin với khả năng cuộn
    // Phương thức hiển thị cửa sổ thông tin với khả năng cuộn
    public void showInfo() {
        JFrame infoFrame = new JFrame("Food and Potion Info");
        infoFrame.setSize(700, 400);  // Tăng kích thước cửa sổ để hiển thị nhiều mục hơn
        infoFrame.setLocationRelativeTo(null);  // Căn giữa cửa sổ trên màn hình
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Đóng cửa sổ mà không ảnh hưởng đến các cửa sổ khác

        // Thêm KeyListener để nhận diện khi người dùng nhấn phím "X"
        infoFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_X) {  // Kiểm tra xem phím nhấn có phải là "X"
                    infoFrame.dispose();  // Đóng cửa sổ thông tin mà không đóng game
                }
            }
        });

        // Tạo JPanel với GridLayout để chia giao diện thành 2 cột (trái cho món ăn, phải cho thuốc)
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));  // 1 hàng, 2 cột (trái và phải)
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Thêm padding

        // Tạo panel cho thông tin món ăn
        JPanel foodPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);  // Gọi phương thức super để vẽ nền
                setBackground(new Color(240, 240, 240));  // Đặt màu nền

                // Hiển thị thông tin món ăn bắt đầu từ (10, 20) và điều chỉnh khoảng cách dọc
                int foodStartY = 20;
                renderFoodInfo(g, 10, foodStartY);
            }
        };

        // Tạo panel cho thông tin thuốc
        JPanel potionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);  // Gọi phương thức super để vẽ nền
                setBackground(new Color(240, 240, 240));  // Đặt màu nền

                // Hiển thị thông tin thuốc bắt đầu từ (10, 20) và điều chỉnh khoảng cách dọc
                int potionStartY = 20;
                renderPotionInfo(g, 10, potionStartY);
            }
        };

        // Thêm cả 2 panel vào infoPanel (trái và phải)
        infoPanel.add(foodPanel);
        infoPanel.add(potionPanel);

        // Thêm JScrollPane để có khả năng cuộn nếu nội dung vượt quá
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        infoFrame.add(scrollPane);  // Thêm JScrollPane vào cửa sổ

        infoFrame.setVisible(true);  // Hiển thị cửa sổ thông tin
    }
}