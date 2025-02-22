package Admin.Category;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Base.Base_Test;
import Driver.Driver_Manager;
import Admin.Login.Admin_Login_Action;
import Utils.Excel_Util;
import Utils.ScreenShotUtil;
import Report.Extend_Report;
import Utils.ConfigUtil;

public class CategoryTestUp extends Base_Test {

    @DataProvider(name = "categoryData")
    public Object[][] getCategoryData() throws IOException, InvalidFormatException {
        Excel_Util excel = new Excel_Util("src/test/resources/data/AFF_A_Data.xlsx", "UpCategory");
        int rowCount = excel.getRowCount();
        Object[][] data = new Object[rowCount - 1][7];

        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, "Name");
            data[i - 1][1] = excel.getCellData(i, "Parent");
            data[i - 1][2] = excel.getCellData(i, "Describe");
            data[i - 1][3] = excel.getCellData(i, "Result");
            data[i - 1][4] = excel.getCellData(i, "Title");
            data[i - 1][5] = excel.getCellData(i, "Link");
            data[i - 1][6] = excel.getCellData(i, "Description");
        }
        return data;
    }

    @Test(dataProvider = "categoryData")
    public void testCategory(String name, String parent, String describe, String result, String title, String link,
            String description) throws Exception {
        Extend_Report.startTest("Kiểm tra sửa danh mục - " + description, description);

        CategoryAction categoryActions = new CategoryAction(Driver_Manager.getDriver());
        Admin_Login_Action loginActions = new Admin_Login_Action(Driver_Manager.getDriver());

        try {
            Excel_Util excelSteps = new Excel_Util("src/test/resources/step/AFF_A_Step.xlsx", "Category");
            int rowCount = excelSteps.getRowCount();

            for (int i = 1; i < rowCount; i++) {
                String action = excelSteps.getCellData(i, "Action Keyword");
                String testData = excelSteps.getCellData(i, "Test Data");

                switch (action.toLowerCase()) {
                case "open":
                    Extend_Report.logInfo("Mở trình duyệt...");
                    break;

                case "navigate":
                    Driver_Manager.getDriver().get(testData);
                    Extend_Report.logInfo("Điều hướng đến " + testData);
                    break;

                case "category":
                    String username = ConfigUtil.getEmail();
                    String password = ConfigUtil.getPassword();
                    loginActions.login(username, password);

                    categoryActions.updateCategory(name, parent, describe);
                    Extend_Report.logInfo("Thực hiện test case: " + description);
                    break;

                case "verifynotion":
                    if (categoryActions.Verifynotion(result)) {
                        Extend_Report.logPass("Kiểm tra thông báo thành công cho: " + result);
                    } else {
                        Extend_Report.logFail("Kiểm tra thông báo thất bại cho: " + result);
                    }
                    break;

                case "verifytitle":
                    if (categoryActions.verifyTitle(title)) {
                        Extend_Report.logPass("Kiểm tra tiêu đề thành công cho: " + title);
                    } else {
                        Extend_Report.logFail("Kiểm tra tiêu đề thất bại cho: " + title);
                    }
                    break;

                case "verifylink":
                    if (categoryActions.verifyLink(link)) {
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
            String screenshotPath = ScreenShotUtil.captureScreenshot(Driver_Manager.getDriver(),
                    "testCategory_Exception", "CategoryTest");
            Extend_Report.attachScreenshot(screenshotPath);
            Extend_Report.logFail("Kiểm tra danh mục thất bại cho: " + description + " với lỗi: " + e.getMessage());
            System.out.println("Ảnh chụp màn hình đã được lưu tại: " + screenshotPath);
            throw e;
        } finally {
            Extend_Report.endReport();
        }
    }
}
