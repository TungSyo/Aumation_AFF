package User.Search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.SheetNameFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("unused")
public class User_Search_Action {
	public WebDriver driver;
	public User_Search_Page searchPage;

	public User_Search_Action(WebDriver driver) {
		this.driver = driver;
		this.searchPage = new User_Search_Page(driver);
	}

	public void entertxtSearch(String search) {
		searchPage.txtSearch.click();
		searchPage.txtSearch.clear();
		searchPage.txtSearch.sendKeys(search);
	}

	public void clickbtnSearch() {
		searchPage.btnSearch.click();
	}

	public void clickCategory() {
		searchPage.btnCategory.click();
	}

	public void searchProduct(String search) {
		entertxtSearch(search);
		clickbtnSearch();
	}

	public boolean verifyNotion(String result) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		boolean isTextFound = false;

		try {
			List<WebElement> allElements = wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), '" + result + "')]")));
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
		String filePath = "src/test/resources/data/User_Data.xlsx";
		FileInputStream fileInputStream = new FileInputStream(new File(filePath));
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet("Search");

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
