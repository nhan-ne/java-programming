package resource.code.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import resource.code.Controller.Launcher;
import resource.code.Model.FoodManager;
import resource.code.Model.PotionManager;
import resource.code.Model.Session;
import resource.code.Model.User;
import resource.code.Model.UserQDB;

public class StartGame extends JFrame {
    private JButton startButton = null;
    private JButton rankButton = null; // Nút Rank mới
    private JButton infoButton = null; // Nút Info mới
    private Image backgroundImage;

    private FoodManager foodManager;
    private PotionManager potionManager;

    public StartGame() {
        setTitle("Welcome to Snake Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tải hình nền
        backgroundImage = new ImageIcon(getClass().getResource("/image/Backround/SnakeGame.png")).getImage();
        if (backgroundImage == null) {
            System.out.println("Image not found!");
        } else {
            System.out.println("Image loaded successfully.");
        }

        // Tạo panel với nền background
        JPanel panel = new BackgroundPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 600));

        // Tạo nút "Start Game"
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setBackground(new Color(0, 204, 102));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(150, 40));

        // Add action listener to check session and either start the game or open the login form
        startButton.addActionListener(e -> {
            String phoneNumberInSession = Session.getTemporaryPhoneNumber();
            System.out.println("Phone number in session: " + phoneNumberInSession); // Debugging output
            if (phoneNumberInSession != null && !phoneNumberInSession.trim().isEmpty()) {
                // If phone number exists in session, proceed with the game
                System.out.println("Valid phone number in session. Starting the game...");
                dispose(); // Close the StartGame window
                new Launcher(phoneNumberInSession); // Start the game
            } else {
                // If no phone number in session, prompt for login
                System.out.println("No valid phone number in session. Opening login form...");
                new LoginForm(); // Open the login form
                dispose(); // Close the StartGame window
            }
        });

        // Tạo nút "Rank" để xem bảng xếp hạng
        rankButton = new JButton("Rank");
        rankButton.setFont(new Font("Arial", Font.PLAIN, 20));
        rankButton.setBackground(new Color(255, 153, 51));
        rankButton.setForeground(Color.WHITE);
        rankButton.setFocusPainted(false);
        rankButton.setBorderPainted(false);
        rankButton.setPreferredSize(new Dimension(150, 40));

        // Thêm sự kiện cho nút Rank
        rankButton.addActionListener(e -> {
            List<User> topPlayers = UserQDB.getTopHighScores(); // Lấy danh sách người chơi top 6
            if (topPlayers != null && !topPlayers.isEmpty()) {
                // Nếu có dữ liệu người chơi, hiển thị bảng xếp hạng
                new Rank(topPlayers); // Hiển thị bảng xếp hạng trong cửa sổ Rank
            } else {
                // Nếu không có người chơi nào trong bảng xếp hạng
                System.out.println("No players in the ranking list.");
            }
        });

        // Tạo nút "Info" để xem thông tin thức ăn và potion
        infoButton = new JButton("Info");
        infoButton.setFont(new Font("Arial", Font.PLAIN, 20));
        infoButton.setBackground(new Color(51, 102, 255));
        infoButton.setForeground(Color.WHITE);
        infoButton.setFocusPainted(false);
        infoButton.setBorderPainted(false);
        infoButton.setPreferredSize(new Dimension(150, 40));

        // Thêm sự kiện cho nút Info
        infoButton.addActionListener(e -> {
            // Mở cửa sổ thông tin game
            System.out.println("Opening game info...");
            new InfoGame(foodManager, potionManager).showInfo(); 
        });

        // Tạo panel chứa các nút và căn giữa
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 50, 0));
        buttonPanel.add(startButton);
        buttonPanel.add(rankButton);
        buttonPanel.add(infoButton); // Thêm nút Info vào panel

        // Thêm panel chứa các nút vào panel chính
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm panel chính vào frame
        add(panel);

        setVisible(true);
    }

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
