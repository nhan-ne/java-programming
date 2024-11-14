package resource.code.View;

import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import resource.code.Model.User;

public class Rank extends JFrame {
    public Rank(List<User> topPlayers) {
        setTitle("Top 6 High Scores");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea rankArea = new JTextArea();
        rankArea.setEditable(false);
        rankArea.setFont(new Font("Arial", Font.PLAIN, 16));

        // Tạo chuỗi hiển thị bảng xếp hạng
        StringBuilder rankText = new StringBuilder();
        for (int i = 0; i < topPlayers.size(); i++) {
            User user = topPlayers.get(i);
            rankText.append("Top ").append(i + 1).append(". Phone: ").append(user.getPhoneNumber())
                    .append(" - HighScore: ").append(user.getHighScore()).append("\n");
        }

        rankArea.setText(rankText.toString());

        // Thêm vào JScrollPane để có thể cuộn
        JScrollPane scrollPane = new JScrollPane(rankArea);
        add(scrollPane);

        setVisible(true);
    }
}
