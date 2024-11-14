package resource.code.Model;

public class Session {
    private static String temporaryPhoneNumber; // Lưu trữ số điện thoại tạm thời

    // Getter cho số điện thoại tạm thời
    public static String getTemporaryPhoneNumber() {
        return temporaryPhoneNumber;
    }

    public static void setTemporaryPhoneNumber(String phoneNumber) {
        // In ra số điện thoại đang được lưu trữ
        System.out.println("Số điện thoại đang lưu trữ: " + (temporaryPhoneNumber != null ? temporaryPhoneNumber : "Chưa có"));
        if (temporaryPhoneNumber != null && !temporaryPhoneNumber.equals(phoneNumber)) {
            System.out.println("Xóa số cũ: " + temporaryPhoneNumber);  // Thông báo khi xóa số cũ
            clearTemporaryPhoneNumber();
        }
        temporaryPhoneNumber = phoneNumber; // Cập nhật số điện thoại mới
        System.out.println("Số điện thoại hiện tại: " + temporaryPhoneNumber);  // Thông báo số điện thoại mới
    }
    

    // Phương thức xóa số điện thoại tạm thời
    public static void clearTemporaryPhoneNumber() {
        temporaryPhoneNumber = null;
    }
}
