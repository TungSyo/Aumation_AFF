package Base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import Driver.Browser_Type;
import Driver.Driver_Manager;
import Driver.Driver_Manager_Profile;
import Utils.ConfigUtil;
import Utils.ScreenShotUtil;
import Report.Extend_Report;

@SuppressWarnings("unused")
public class Base_Test {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws IOException, URISyntaxException {
        Extend_Report.startReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {
        System.out.println("⚡ Đang thiết lập trình duyệt...");

        String browserConfig = ConfigUtil.getProperty("browser");
        if (browserConfig == null || browserConfig.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "⚠ Lỗi: Cấu hình 'browser' không được tìm thấy hoặc để trống trong file config.properties!");
        }

        Browser_Type browser;
        try {
            browser = Browser_Type.valueOf(browserConfig.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("⚠ Lỗi: Trình duyệt '" + browserConfig
                    + "' không hợp lệ! Hãy kiểm tra lại file config.properties.");
        }

        System.out.println("🌍 Đang khởi chạy trình duyệt: " + browser);

        // Đảm bảo gọi Driver_Manager.initDriver() trước khi sử dụng getDriver().
        Driver_Manager.initDriver(browser);

    

        Driver_Manager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        Driver_Manager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        System.out.println("✅ Page Load Timeout: " + 20 + " giây");
        System.out.println("✅ Implicit Wait: " + 15 + " giây");
    }


    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void handleVerification(boolean condition, String type, String value) {
        if (condition) {
            Extend_Report.logPass("Kiểm tra " + type + " thành công cho: " + value);
        } else {
            Extend_Report.logFail("Kiểm tra " + type + " thất bại cho: " + value);
        }
    }

    protected void handleTestException(Exception e, String description) throws IOException {
        String screenshotPath = ScreenShotUtil.captureScreenshot(Driver_Manager.getDriver(), "testLogin_Exception",
                "LoginTest");
        Extend_Report.attachScreenshot(screenshotPath);
        Extend_Report.logFail("Kiểm tra đăng nhập thất bại cho: " + description + " với lỗi: " + e.getMessage());
        System.out.println("Ảnh chụp màn hình đã được lưu tại: " + screenshotPath);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (Driver_Manager.getDriver() != null) {
            try {
                System.out.println("🛑 Đóng trình duyệt...");
                Driver_Manager.quitDriver();
            } catch (Exception e) {
                System.out.println("🚨 Lỗi khi đóng trình duyệt: " + e.getMessage());
            }
        } else {
            System.out.println("🚫 Trình duyệt đã được đóng hoặc không tồn tại.");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        Extend_Report.endReport();
    }

}