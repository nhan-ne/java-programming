package resource.code.Model;

public class Session {
    private static String temporaryPhoneNumber; // Lưu trữ số điện thoại tạm thời

    // Getter và Setter cho số điện thoại tạm thời
    public static String getTemporaryPhoneNumber() {
        return temporaryPhoneNumber;
    }

    public static void setTemporaryPhoneNumber(String phoneNumber) {
        temporaryPhoneNumber = phoneNumber;
    }

    // Phương thức xóa số điện thoại tạm thời
    public static void clearTemporaryPhoneNumber() {
        temporaryPhoneNumber = null;
    }
}
