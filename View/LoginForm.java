package resource.code.View;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import resource.code.Model.Session;

public class LoginForm extends JFrame {
    private final JTextField phoneNumberField;
    private final JButton loginButton;

    public LoginForm() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cài đặt Layout
        setLayout(new GridLayout(3, 1));

        // Nhập số điện thoại với chức năng placeholder
        phoneNumberField = new JTextField();
        phoneNumberField.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneNumberField.setText("Enter your phone number"); // Văn bản placeholder

        // Lắng nghe sự kiện Focus để loại bỏ placeholder khi lấy focus
        phoneNumberField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (phoneNumberField.getText().equals("Enter your phone number")) {
                    phoneNumberField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (phoneNumberField.getText().isEmpty()) {
                    phoneNumberField.setText("Enter your phone number");
                }
            }
        });

        // Nút đăng nhập
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.addActionListener(e -> login());

        // Thêm các thành phần vào frame
        add(new JLabel("Phone Number:", JLabel.CENTER));
        add(phoneNumberField);
        add(loginButton);

        setVisible(true);
    }

    private void login() {
        String phoneNumber = phoneNumberField.getText().trim(); // Loại bỏ các khoảng trắng thừa
        if (isValidPhoneNumber(phoneNumber)) {
            // Lưu số điện thoại vào session hoặc chuyển trực tiếp
            Session.setTemporaryPhoneNumber(phoneNumber);
            
            // Đóng form đăng nhập và bắt đầu trò chơi
            dispose();
            new StartGame(); // Bắt đầu trò chơi sau khi đăng nhập
        } else {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Kiểm tra số điện thoại hợp lệ
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false; // Trả về false nếu số điện thoại trống hoặc null
        }
        String regex = "^0[3|5|7|8|9][0-9]{8}$"; // Phải bắt đầu bằng '0' và theo sau là 9 chữ số
        return phoneNumber.matches(regex);
    }
}
