package resource.code.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import resource.code.Controller.GameController;

public class StartGame extends JFrame {
    private final JButton startButton;
    private final Image backgroundImage;

    public StartGame() {
        setTitle("Welcome to Snake Game");
        setSize(600, 600); // Kích thước của cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa cửa sổ

        // Tải hình nền
        backgroundImage = new ImageIcon(getClass().getResource("/image/Backround/SnakeGame.png")).getImage();
        if (backgroundImage == null) {
            System.out.println("Image not found!");
        } else {
            System.out.println("Image loaded successfully.");
        }

        // Tạo một panel để chứa các thành phần
        JPanel panel = new BackgroundPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 600)); // Đảm bảo kích thước khung hình

        // Tạo button "Start Game"
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setBackground(new Color(0, 204, 102));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        
        // Đặt kích thước cho button
        startButton.setPreferredSize(new Dimension(150, 40)); // Kích thước nhỏ hơn
        startButton.addActionListener(new StartGameAction());

        // Tạo một panel cho button và căn giữa nó
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Làm cho panel trong suốt
        buttonPanel.setBorder(new EmptyBorder(0, 0, 50, 0)); // Thêm khoảng cách phía dưới
        buttonPanel.add(startButton);

        // Thêm các thành phần vào panel chính
        panel.add(buttonPanel, BorderLayout.SOUTH); // Đặt button ở dưới cùng
        
        // Thêm panel vào khung
        add(panel);
        
        // Hiển thị khung
        setVisible(true);
    }

    // Panel tùy chỉnh để vẽ hình nền
    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Vẽ hình nền và kéo dãn để vừa kích thước panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600); // Đảm bảo kích thước khớp với khung
        }
    }

    // Action listener để bắt đầu trò chơi
    private class StartGameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); // Đóng cửa sổ bắt đầu
            new GameController(); // Khởi động trò chơi
        }
    }
}
