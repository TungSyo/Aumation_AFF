package User.Register;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import User.Register.User_Register_Page;
import Base.Base_Page;
import Base.Base_Test;
import User.Login.User_Login_Page;
import Utils.ConfigUtil;
import Utils.Email_Reader;
import Utils.Login_Google;
import Utils.OCRUtils;
import Utils.Otp_Extractor;
import Utils.ScreenShotUtil;

@SuppressWarnings("unused")
public class User_Register_Action {
    // Instance variables
    private WebDriver driver;
    private User_Register_Page registerPage;
    private Base_Page basePage;
    private Base_Test baseTest;
    private User_Login_Page loginPage;
    private Login_Google loginGoogle;
    public boolean shouldLoginGoogle = false;

    public User_Register_Action(WebDriver driver) {
        this.driver = driver;
        this.registerPage = new User_Register_Page(driver);
        this.basePage = new Base_Page(driver);
        this.baseTest = new Base_Test();
        this.loginGoogle = new Login_Google(driver);
        this.loginPage = new User_Login_Page(driver);
    }

    public void setShouldLoginGoogle(boolean value) {
        this.shouldLoginGoogle = value;
    }

    public boolean getShouldLoginGoogle() {
        return shouldLoginGoogle;
    }

    public void clickTaotk() {
        registerPage.btnTaotk.click();

        if (isTextPresent("XÁC THỰC OTP")) {
            setShouldLoginGoogle(true);
        } else {
            System.out.println("🚀 Không cần đăng nhập Google. Tiếp tục bước tiếp theo.");
        }
    }

    public void clickCheckox() {
        registerPage.cbDongy.click();
    }

    public void clickRegister() {
        basePage.linkRegister.click();
    }

    public void clickAccount() {
        basePage.btnAccount.click();
        baseTest.sleep(500);

        try {
            if (basePage.btnLogout.isDisplayed()) {
                basePage.btnLogout.click();
                baseTest.sleep(5000);
            }
        } catch (Exception e) {

        }

    }

    public void enterOTP(String otp) {
        registerPage.txtOtp.click();

        List<WebElement> otpInputs = driver.findElements(By.xpath("//div[@class='otp-inputs']/input"));
        int length = Math.min(otp.length(), otpInputs.size()); // Đảm bảo không bị lỗi index out of bounds

        for (int i = 0; i < length; i++) {
            otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
        }
    }

    public void clickXacnhan() {
        registerPage.btnXacnhan.click();
    }

    // Validate
    public void enterName(String name) {
        registerPage.txtName.sendKeys(name);
    }

    public void enterSdt(String sdt) {
        registerPage.txtSdt.sendKeys(sdt);
    }

    public void enterEmail(String email) {
        registerPage.txtEmail.sendKeys(email);
    }

    public void enterCmnd(String cmnd) {
        registerPage.txtCMND.sendKeys(cmnd);
    }

    public void enterPass(String pass) {
        registerPage.txtPass.sendKeys(pass);
    }

    // Các trường không bắt buộc
    public void enterMgt(String mgt) {
        registerPage.txtMgt.sendKeys(mgt);
    }

    public void enterCity(String city) {
        registerPage.txtCity.sendKeys(city);
    }

    public void enterDistrict(String district) {
        registerPage.txtDistrict.sendKeys(district);
    }

    public void enterWard(String ward) {
        registerPage.txtWard.sendKeys(ward);
    }

    public void enterLocation(String location) {
        registerPage.txtLocation.sendKeys(location);
    }

    public void enterMst(String mst) {
        registerPage.txtMst.sendKeys(mst);
    }

    public void enterDate(String date) {
        registerPage.txtDate.sendKeys(date);
    }

    public void enterBank(String bank) {
        registerPage.txtBank.sendKeys(bank);
    }

    public void enterStk(String stk) {
        registerPage.txtStk.sendKeys(stk);
    }

    public void register(String name, String sdt, String email, String cmnd, String pass, String mgt,
            String city, String district, String ward, String location, String mst,
            String date, String bank, String stk, String result) {
        clickAccount();
        clickRegister();
        baseTest.sleep(1000);
        enterName(name);
        enterSdt(sdt);
        enterEmail(email);
        enterCmnd(cmnd);
        enterPass(pass);

        enterMgt(mgt);
        enterCity(city);
        enterDistrict(district);
        enterWard(ward);
        enterLocation(location);
        enterMst(mst);
        enterDate(date);
        enterBank(bank);
        enterStk(stk);

        clickCheckox();
        clickTaotk();

        if (shouldLoginGoogle) {
            // String host = ConfigUtil.getProperty("pop3.host");
            // String username = email;
            // String password = ConfigUtil.getProperty("pop3.pass");
            String host = "mail.pop3.host"; // Đảm bảo host đúng
            String username = email;
            String password = "s2kutebaby"; // Mật khẩu email

            // Thử kết nối trực tiếp thay vì từ file cấu hình

            try {
                System.out.println("📨 Đang kiểm tra hộp thư...");
                String emailContent = Email_Reader.readLatestEmail(host, username, password);

                if (emailContent != null) {
                    String otp = Otp_Extractor.extractOtpFromEmail(emailContent);
                    if (otp != null && !otp.isEmpty()) {
                        System.out.println("✅ OTP tìm thấy: " + otp);
                        enterOTP(otp);
                        clickXacnhan();
                    } else {
                        System.err.println("⚠️ Không lấy được OTP!");
                    }
                } else {
                    System.err.println("⚠️ Không có email nào trong hộp thư.");
                }
            } catch (Exception e) {
                System.err.println("❌ Lỗi khi đọc email qua POP: " + e.getMessage());
            }
        }

        // Sử dụng Profile để đăng nhập vào google và lấy OTP
        // if (shouldLoginGoogle) {
        // loginGoogle.openNewTabAndAccessGoogle();
        // loginGoogle.loginGoogle(email);

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // try {
        // wait.until(ExpectedConditions
        // .presenceOfElementLocated(By.xpath("//div[@class='T-I T-I-KE
        // L3'][contains(.,'Soạn thư')]")));
        // System.out.println("Login thành công!");
        // } catch (TimeoutException e) {
        // System.out.println("Login không thành công!");
        // }
        // String otp = loginGoogle.checkEmailAndGetOTP();
        // if (otp == null) {
        // System.out.println("⚠️ Không lấy được OTP!");
        // return;
        // }
        // loginGoogle.deleteMail();
        // loginGoogle.switchBackToOriginalTab();

        // enterOTP(otp);
        // clickXacnhan();
        // }

    }

    public boolean verifyNotion(String result) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        boolean isTextFound = false;

        try {
            List<WebElement> allElements = wait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), '" + result +
                            "')]")));
            for (WebElement element : allElements) {
                String elementText = element.getText().trim();
                if (!elementText.isEmpty() && elementText.contains(result)) {
                    isTextFound = true;
                    break;
                }
            }
        } catch (Exception e) {
            isTextFound = false;
        }
        return isTextFound;
    }

    public boolean verifyLink(String link) {
        String currentUrl = driver.getCurrentUrl();

        String decodedExpected = URLDecoder.decode(link.trim(), StandardCharsets.UTF_8);
        String decodedActual = URLDecoder.decode(currentUrl.trim(), StandardCharsets.UTF_8);

        System.out.println("[DEBUG] Expected URL: " + decodedExpected);
        System.out.println("[DEBUG] Actual URL: " + decodedActual);

        return decodedActual.equalsIgnoreCase(decodedExpected);
    }

    public boolean verifyTitle(String title) {
        String actualTitle = driver.getTitle();
        System.out.println("[DEBUG] Expected Title: " + title);
        System.out.println("[DEBUG] Actual Title: " + actualTitle);
        return actualTitle.equals(title);
    }

    public List<Object[]> getTestData() throws IOException {
        List<Object[]> testData = new ArrayList<>();
        String filePath = "src/test/resources/data/User_Data.xlsx";
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Register");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;
            String title = row.getCell(15).getStringCellValue();
            String link = row.getCell(16).getStringCellValue().trim();
            testData.add(new Object[] { title, link });
        }

        workbook.close();
        fileInputStream.close();
        return testData;
    }

    public boolean isTextPresent(String expectedText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Chờ tối đa 3 giây
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'" + expectedText + "')]")));
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("⚠️ Không tìm thấy phần tử chứa: " + expectedText + ". Tiếp tục bước tiếp theo...");
            return false;
        }
    }
}
