package Admin.Login;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.Base_Page;
import Report.Extend_Report;

public class Admin_Login_Action {
	public WebDriver driver;
	public Base_Page basePage;

	public Admin_Login_Action(WebDriver driver) {
		this.driver = driver;
		this.basePage = new Base_Page(driver);
	}

	public void enterUsername(String email) {
		basePage.txtAUser.clear();
		basePage.txtAUser.sendKeys(email);
        Extend_Report.logInfo("Nhập email: " + email);
	}

	public void enterPassword(String password) {
		basePage.txtAPass.clear();
		basePage.txtAPass.sendKeys(password);
        Extend_Report.logInfo("Nhập password: " + password);
	}

	public void clickLogin() {
		basePage.btnALogin.click();
        Extend_Report.logInfo("Nhấn đăng nhập");
	}

	public void login(String email, String password) {
		enterUsername(email);
		enterPassword(password);
		clickLogin();
	}

	
	public boolean Verifynotion(String expectedText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		boolean isTextFound = false;

		try {
			List<WebElement> allElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), '" + expectedText + "')]")));
			for (WebElement element : allElements) {
				String elementText = element.getText().trim();
				if (!elementText.isEmpty() && elementText.contains(expectedText)) {
					isTextFound = true;
					break;
				}
			}
		} catch (Exception e) {
			isTextFound = false;
		}

		return isTextFound;
	}

   @SuppressWarnings("unused")
public boolean verifyLink(String expectedLink) {
		String currentUrl = driver.getCurrentUrl();

		String trimmedExpectedLink = expectedLink.trim();
		String trimmedCurrentUrl = currentUrl.trim();
		
		String decodedExpected = URLDecoder.decode(expectedLink, StandardCharsets.UTF_8);
		String decodedActual = URLDecoder.decode(currentUrl, StandardCharsets.UTF_8);
	 
		System.out.println("[DEBUG] Expected URL: " + decodedExpected);
		System.out.println("[DEBUG] Actual URL: " + decodedActual);
	 
		return decodedActual.equalsIgnoreCase(decodedExpected);
	 }
	 
	 public boolean verifyTitle(String expectedTitle) {
		String actualTitle = driver.getTitle();
		System.out.println("[DEBUG] Expected Title: " + expectedTitle);
		System.out.println("[DEBUG] Actual Title: " + actualTitle);
		return actualTitle.equals(expectedTitle);
	 }
	

	public List<Object[]> getSearchTestData() throws IOException {
		List<Object[]> testData = new ArrayList<>();
		String filePath = "src/test/resources/data/Admin_Data.xlsx";
		FileInputStream fileInputStream = new FileInputStream(new File(filePath));
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet("Login");

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;
			String keyword = row.getCell(0).getStringCellValue();
			String expectedTitle = row.getCell(2).getStringCellValue();
			String expectedLink = row.getCell(3).getStringCellValue().trim();
			testData.add(new Object[] { keyword, expectedTitle, expectedLink });
		}

		workbook.close();
		fileInputStream.close();
		return testData;
	}

}
