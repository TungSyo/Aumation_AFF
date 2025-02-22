package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Otp_Extractor {

    // Phương thức để trích xuất OTP từ nội dung email
    public static String extractOtpFromEmail(String emailContent) {

        // Kiểm tra xem nội dung email có chứa từ khóa nhất định không
        if (emailContent.contains("Your verification code") && emailContent.contains("AFF-HONIVY is")) {
            System.out.println("✅ Raw Message found: [" + emailContent + "]");

            // Sử dụng regex để tìm mã OTP dạng số trong email
            Pattern otpPattern = Pattern.compile("\\b\\d{6}\\b");  // Giả sử OTP là 6 chữ số
            Matcher matcher = otpPattern.matcher(emailContent);
            if (matcher.find()) {
                String otp = matcher.group();  // Lấy OTP từ kết quả tìm thấy
                System.out.println("🔢 OTP extracted: [" + otp + "]");
                return otp;
            } else {
                System.out.println("⏳ Không tìm thấy OTP.");
                return null;
            }
        } else {
            System.out.println("⏳ Không tìm thấy OTP.");
            return null;
        }
    }
}
