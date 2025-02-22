package User.Register;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Base.Base_Test;
import Driver.Driver_Manager;
import User.Login.User_Login_Action;
import Utils.ConfigUtil;
import Utils.Excel_Util;
import Utils.Login_Google;
import Utils.ScreenShotUtil;
import Report.Extend_Report;
import User.Register.User_Register_Action;

@SuppressWarnings("unused")
public class User_Register_Test extends Base_Test {

    @DataProvider(name = "registerData")
    private Object[][] getLoginData(ITestContext context) throws IOException, InvalidFormatException {
        String[] includedGroups = context.getIncludedGroups();
        validateIncludedGroups(includedGroups);

        Excel_Util excel = new Excel_Util("src/test/resources/data/User_Data.xlsx", "Register");
        return filterLoginData(excel, includedGroups);
    }
    private Object[][] filterLoginData(Excel_Util excel, String[] includedGroups) {
        if (excel == null) {
            throw new IllegalArgumentException("Đối tượng excel_util không được khởi tạo.");
        }

        int rowCount = excel.getRowCount();
        List<Object[]> filteredData = new ArrayList<>();
        for (int i = 1; i < rowCount; i++) {
            String name = excel.getCellData(i, "Name");
            String sdt = excel.getCellData(i, "Sdt");
            String email = excel.getCellData(i, "Email");
            String cmnd = excel.getCellData(i, "Cmnd");
            String pass = excel.getCellData(i, "Pass");
            String mgt = excel.getCellData(i, "Mgt");
            String city = excel.getCellData(i, "City");
            String district = excel.getCellData(i, "District");
            String ward = excel.getCellData(i, "Ward");
            String location = excel.getCellData(i, "Location");
            String mst = excel.getCellData(i, "Mst");
            String date = excel.getCellData(i, "Date");
            String bank = excel.getCellData(i, "Bank");
            String stk = excel.getCellData(i, "Stk");
            String result = excel.getCellData(i, "Result");
            String title = excel.getCellData(i, "Title");
            String link = excel.getCellData(i, "Link");
            String description = excel.getCellData(i, "Description");
            String testType = excel.getCellData(i, "TestType");

            for (String group : includedGroups) {
                if (testType.equalsIgnoreCase(group)) {
                    filteredData.add(new Object[] { name, sdt, email, cmnd, pass, mgt, city, district, ward, location,
                            mst, date, bank, stk, result, title, link, description, testType });
                    break;
                }
            }
        }
        return filteredData.toArray(new Object[0][]);
    }
    private String[] validateIncludedGroups(String[] includedGroups) {
        if (includedGroups == null || includedGroups.length == 0) {
            System.out.println("Không có nhóm nào được chỉ định. Sử dụng nhóm mặc định: Success, Fail");
            return new String[] { "Success", "Fail" };
        }
        return includedGroups;
    }
    
    private void validateTestType(String testType) {
        if (!testType.equalsIgnoreCase("Success") && !testType.equalsIgnoreCase("Fail")) {
            throw new IllegalArgumentException("TestType không hợp lệ: " + testType);
        }
    }
    @Test(dataProvider = "registerData", groups = { "Success", "Fail" })
    public void registerTest(String name, String sdt, String email, String cmnd, String pass, String mgt, String city,
            String district, String ward, String location, String mst, String date, String bank, String stk,
            String result, String title, String link, String description, String testType) throws Exception {
        validateTestType(testType);
        Extend_Report.startTest("User Register Test - " + description, determineCategory(testType));
        
        try {
            User_Register_Action registerActions = new User_Register_Action(Driver_Manager.getDriver());
            Excel_Util excelSteps = new Excel_Util("src/test/resources/step/Step.xlsx", "Step");
            int rowCount = excelSteps.getRowCount();
            
            for (int i = 1; i < rowCount; i++) {
                String action = excelSteps.getCellData(i, "Action Keyword").toLowerCase();
                
                switch (action) {
                    case "open":
                        Extend_Report.logInfo("Mở trình duyệt...");
                        break;
                    case "navigate":
                        String url_user = ConfigUtil.getProperty("url_user");
                        Driver_Manager.getDriver().get(url_user);
                        Extend_Report.logInfo("Điều hướng đến: " + url_user);
                        break;
                    case "action":
                        registerActions.register(name, sdt, email, cmnd, pass, mgt, city, district, ward, location, mst, date, bank, stk, result);
                        Extend_Report.logInfo("Thực hiện test case: " + description);
                        break;
                    case "verifynotion":
                        handleVerification(registerActions.verifyNotion(result), "thông báo", result);
                        break;
                    case "verifytitle":
                        handleVerification(registerActions.verifyTitle(title), "tiêu đề", title);
                        break;
                    case "verifylink":
                        handleVerification(registerActions.verifyLink(link), "link", link);
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
        } finally {
            Extend_Report.endTest();
        }
    }
    
    private String determineCategory(String testType) {
        return testType.equalsIgnoreCase("Fail") ? "Register_Test_Data_Fail" : "Login_Test_Data_Pass";
    }
}
