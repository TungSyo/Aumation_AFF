package Admin.Login;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Base.Base_Test;
import Driver.Driver_Manager;
import User.Login.User_Login_Action;
import Utils.ConfigUtil;
import Utils.Excel_Util;
import Utils.ScreenShotUtil;
import Report.Extend_Report;

@SuppressWarnings("unused")
public class Admin_Login_Test extends Base_Test {

    @DataProvider(name = "loginData", parallel = false)
    public Object[][] getLoginData() throws IOException, InvalidFormatException {
        Excel_Util excel = new Excel_Util("src/test/resources/data/Admin_Data.xlsx", "Login");
        int rowCount = excel.getRowCount();
        Object[][] data = new Object[rowCount - 1][6];
        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, "Email");
            data[i - 1][1] = excel.getCellData(i, "Password");
            data[i - 1][2] = excel.getCellData(i, "Result");
            data[i - 1][3] = excel.getCellData(i, "Title");
            data[i - 1][4] = excel.getCellData(i, "Link");
            data[i - 1][5] = excel.getCellData(i, "Description");
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password, String result, String title, String link, String description)
            throws Exception {

        Admin_Login_Action loginActions = new Admin_Login_Action(Driver_Manager.getDriver());

        try {
            // Bắt đầu test case
            Extend_Report.startTest("Admin Login Test - " + description, "Admin_Login");

            // Đọc các bước kiểm thử từ file Excel
            Excel_Util excelSteps = new Excel_Util("src/test/resources/step/Step.xlsx", "Step");
            int rowCount = excelSteps.getRowCount();
            for (int i = 1; i < rowCount; i++) {
                String action = excelSteps.getCellData(i, "Action Keyword");
                switch (action.toLowerCase()) {
                    case "open":
                        Extend_Report.logInfo("Mở trình duyệt...");
                        break;
                        
                    case "navigate":
                        String url_admin = ConfigUtil.getProperty("url_admin");
                        Driver_Manager.getDriver().get(url_admin);
                        Extend_Report.logInfo("Điều hướng đến: " + url_admin);
                        break;

                    case "action":
                        loginActions.login(email, password);
                        Extend_Report.logInfo("Thực hiện test case: " + description);
                        break;
                    case "verifynotion":
                        if (loginActions.Verifynotion(result)) {
                            Extend_Report.logPass("Kiểm tra thông báo thành công cho: " + result);
                        } else {
                            Extend_Report.logFail("Kiểm tra thông báo thất bại cho: " + result);
                        }
                        break;
                    case "verifytitle":
                        if (loginActions.verifyTitle(title)) {
                            Extend_Report.logPass("Kiểm tra tiêu đề thành công cho: " + title);
                        } else {
                            Extend_Report.logFail("Kiểm tra tiêu đề thất bại cho: " + title);
                        }
                        break;
                    case "verifylink":
                        if (loginActions.verifyLink(link)) {
                            Extend_Report.logPass("Kiểm tra link thành công cho: " + link);
                        } else {
                            Extend_Report.logFail("Kiểm tra link thất bại cho: " + link);
                        }
                        break;
                    case "close":
                        Extend_Report.logInfo("Đóng trình duyệt...");
                        break;
                    default:
                        throw new IllegalArgumentException("Hành động chưa xác định: " + action);
                }
            }
        } catch (Exception e) {
            String screenshotPath = ScreenShotUtil.captureScreenshot(Driver_Manager.getDriver(), "testLogin_Exception",
                    "LoginTest");
            Extend_Report.attachScreenshot(screenshotPath);
            Extend_Report.logFail("Kiểm tra đăng nhập thất bại cho: " + email + " với lỗi: " + e.getMessage());
            System.out.println("Ảnh chụp màn hình đã được lưu tại: " + screenshotPath);
            throw e;
        } finally {
            Extend_Report.endTest();
        }
    }
}