package resource.code.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class User {
    private String phoneNumber; // Số điện thoại
    private int highScore; // Điểm cao nhất

    // Constructor chỉ khởi tạo với số điện thoại và điểm cao
    public User(String phoneNumber, int highScore) {
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
        this.highScore = highScore;
    }

    // Phương thức kiểm tra số điện thoại hợp lệ
    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^0[3|5|7|8|9][0-9]{8}$"; // Kiểm tra số điện thoại Việt Nam
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    // Getter và Setter cho số điện thoại và điểm cao
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
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
