package User.Search;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Base.Base_Test;
import Driver.Driver_Manager;
import Utils.ConfigUtil;
import Utils.Excel_Util;
import Utils.ScreenShotUtil;
import Report.Extend_Report;

public class User_Search_Test extends Base_Test {

    @DataProvider(name = "searchData", parallel = false) 
    public Object[][] getSearchData() throws IOException, InvalidFormatException {
        Excel_Util excel = new Excel_Util("src/test/resources/data/User_Data.xlsx", "Search");
        int rowCount = excel.getRowCount();
        Object[][] data = new Object[rowCount - 1][5];
        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, "Search");
            data[i - 1][1] = excel.getCellData(i, "Result");
            data[i - 1][2] = excel.getCellData(i, "Title");
            data[i - 1][3] = excel.getCellData(i, "Link");
            data[i - 1][4] = excel.getCellData(i, "Description");
        }
        return data;
    }

    @Test(dataProvider = "searchData")
    public void testSearch(String search, String result, String title, String link, String description)
        throws Exception {

    
        
        User_Search_Action searchActions = new User_Search_Action(Driver_Manager.getDriver());

        try {
            Extend_Report.startTest("Search Test - " + description, "Search_Product_Data_Pass");

            
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
                        Extend_Report.logInfo("Điều hướng đến: " + url_user);
                        break;
                    case "action":
                        searchActions.searchProduct(search);
                        Extend_Report.logInfo("Thực hiện test case: " + description);
                        break;
                    case "verifynotion":
                        if (searchActions.verifyNotion(result)) {
                            Extend_Report.logPass("Kiểm tra kết quả thành công cho: " + result);
                        } else {
                            Extend_Report.logFail("Kiểm tra kết quả thất bại cho: " + result);
                        }
                        break;
                    case "verifytitle":
                        if (searchActions.verifyTitle(title)) {
                            Extend_Report.logPass("Kiểm tra tiêu đề thành công cho: " + title);
                        } else {
                            Extend_Report.logFail("Kiểm tra tiêu đề thất bại cho: " + title);
                        }
                        break;
                    case "verifylink":
                        if (searchActions.verifyLink(link)) {
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
            // Xử lý ngoại lệ và chụp ảnh màn hình
            String screenshotPath = ScreenShotUtil.captureScreenshot(Driver_Manager.getDriver(), "testSearch_Exception",
                    "SearchTest");
            Extend_Report.attachScreenshot(screenshotPath);
            Extend_Report.logFail("Kiểm tra tìm kiếm thất bại cho: " + description + " với lỗi: " + e.getMessage());
            System.out.println("Ảnh chụp màn hình đã được lưu tại: " + screenshotPath);
            throw e;
        } finally {
            // Kết thúc test case
            Extend_Report.endTest();
        }
    }
}