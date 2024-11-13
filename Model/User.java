package resource.code.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class User {
    private String phoneNumber; // Số điện thoại
    private int highScore; // Điểm cao nhất

    // Constructor để khởi tạo User với số điện thoại và điểm cao
    public User(String phoneNumber, int highScore) {
        // Kiểm tra xem số điện thoại có hợp lệ không
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
            Session.setTemporaryPhoneNumber(phoneNumber);  // Lưu số điện thoại tạm thời
        } else {
            // Nếu số điện thoại không hợp lệ, ném lỗi
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
        this.highScore = highScore;
    }

    // Phương thức kiểm tra số điện thoại hợp lệ
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Biểu thức chính quy để kiểm tra số điện thoại (số điện thoại Việt Nam)
        String regex = "^0[3|5|7|8|9][0-9]{8}$"; // Phải bắt đầu bằng '0' và theo sau là 9 chữ số
        Pattern pattern = Pattern.compile(regex); // Biểu thức chính quy
        Matcher matcher = pattern.matcher(phoneNumber); // Kiểm tra xem số điện thoại có khớp không
        return matcher.matches(); // Trả về true nếu hợp lệ, false nếu không hợp lệ
    }

    // Getter và Setter cho số điện thoại và điểm cao
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // Kiểm tra nếu số điện thoại hợp lệ
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
            Session.setTemporaryPhoneNumber(phoneNumber);  // Cập nhật số điện thoại tạm thời
        } else {
            // Nếu số điện thoại không hợp lệ, ném lỗi
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
