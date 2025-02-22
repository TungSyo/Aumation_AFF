package User.Login;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

public class User_Login_Test extends Base_Test {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException, InvalidFormatException {
        Excel_Util excel = new Excel_Util("src/test/resources/data/User_Data.xlsx", "Login");
        int rowCount = excel.getRowCount();
        Object[][] data = new Object[rowCount - 1][7];

        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, "Email");
            data[i - 1][1] = excel.getCellData(i, "Password");
            data[i - 1][2] = excel.getCellData(i, "Result");
            data[i - 1][3] = excel.getCellData(i, "Title");
            data[i - 1][4] = excel.getCellData(i, "Link");
            data[i - 1][5] = excel.getCellData(i, "Description");
            data[i - 1][6] = excel.getCellData(i, "TestType");
        }

        return data;
    }

    @Test(dataProvider = "loginData", groups = { "Success", "Fail" })
    public void testLogin(String email, String password, String result, String title, String link, String description,
            String testType)
            throws Exception {

        String category = testType.equalsIgnoreCase("Fail") ? "Login_Data_Fail" : "Login_Data_Pass";
        Extend_Report.startTest("Login Test - " + description, category);
        User_Login_Action loginActions = new User_Login_Action(Driver_Manager.getDriver());

        try {
            Excel_Util excelSteps = new Excel_Util("src/test/resources/step/Step.xlsx", "Step");
            int rowCount = excelSteps.getRowCount();

            for (int i = 1; i < rowCount; i++) {
                String action = excelSteps.getCellData(i, "Action Keyword");

                switch (action.toLowerCase()) {
                    case "open":
                        Extend_Report.logInfo("Mở trình duyệt...");
                        break;

                    case "navigate":
                        String url_user = ConfigUtil.getProperty("url_user");
                        Driver_Manager.getDriver().get(url_user);
                        Extend_Report.logInfo("Điều hướng đến " + url_user);
                        break;

                    case "action":
                        loginActions.login(email, password);
                        Extend_Report.logInfo("Thực hiện test case: " + description);
                        break;

                    case "verifynotion":
                        handleVerification(loginActions.verifyNotion(result), "thông báo", result);
                        break;

                    case "verifytitle":
                        handleVerification(loginActions.verifyTitle(title), "tiêu đề", title);
                        break;

                    case "verifylink":
                        handleVerification(loginActions.verifyLink(link), "link", link);
                        break;
                    case "close":
                        Extend_Report.logInfo("Đóng trình duyệt...");
                        break;
                    default:
                        throw new IllegalArgumentException("Hành động chưa xác định: " + action);
                }
            }
        } catch (Exception e) {
            handleTestException(e, description);
            throw e;
        }
    }
}