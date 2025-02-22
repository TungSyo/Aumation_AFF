package Admin.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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

public class CategoryAction {
    public WebDriver driver;
    public CategoryPage categoryPage;
    public Base_Page basePage;

    public CategoryAction(WebDriver driver) {
        this.driver = driver;
        this.categoryPage = new CategoryPage(driver);
        this.basePage = new Base_Page(driver);
    }

    public void MenuProduct() {
        clickmnProduct();
        clickmnCategory();
    }

    public void clickmnProduct() {
        basePage.menuProduct.click();
    }

    public void clickmnCategory() {
        basePage.menuListCategory.click();
    }

    public void clickaddCate() {
        categoryPage.btnAddCate.click();
    }

    public void clickUpCate() {
        categoryPage.btnUpCate.click();
    }

    public void enterNamecate(String name) {
        if (categoryPage.txtNameCate.isDisplayed() && categoryPage.txtNameCate.isEnabled()) {
            waitUntilVisible(categoryPage.txtNameCate);
            categoryPage.txtNameCate.clear();
            categoryPage.txtNameCate.sendKeys(name);
        } else {
            System.out.println("Trường văn bản không thể thao tác");
        }
    }

    public boolean selectParent(String parent) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> allElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//*[contains(text(), '" + parent + "')]")));
            
            for (WebElement element : allElements) {
                if (element.getText().contains(parent)) {
                    element.click();
                    return true;
                }
            }
            System.out.println("Không tìm thấy danh mục cha: " + parent);
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khi chọn danh mục cha: " + e.getMessage());
            return false;
        }
    }

    public void enterDescribe(String describe) {
        waitUntilVisible(categoryPage.txtDescribe);
        categoryPage.txtDescribe.clear();
        categoryPage.txtDescribe.sendKeys(describe);
    }

    public void clickSave() {
        waitUntilClickable(categoryPage.btnSave);
        categoryPage.btnSave.click();
    }

    public void addCategory(String name, String parent, String describe) {
        MenuProduct();
        clickaddCate();
        enterNamecate(name);
        selectParent(parent);
        enterDescribe(describe);
        clickSave();
    }

    public void updateCategory(String name, String parent, String describe) {
        MenuProduct();
        clickUpCate();

        waitUntilVisible(categoryPage.txtNameCate);
        categoryPage.txtNameCate.clear();
        waitUntilVisible(categoryPage.txtNameCate);
        categoryPage.txtNameCate.sendKeys(name);

        if (!selectParent(parent)) {
            System.out.println("Không tìm thấy danh mục cha: " + parent);
        }

        waitUntilVisible(categoryPage.txtDescribe);
        categoryPage.txtDescribe.clear();
        categoryPage.txtDescribe.sendKeys(describe);

        clickSave();
    }

    public void waitUntilClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean Verifynotion(String expectedText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		boolean isTextFound = false;

		try {
			List<WebElement> allElements = wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), '" + expectedText + "')]")));
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

	public boolean verifyTitle(String title) {
		try {
			String filePath = "src/test/resources/data/AFF_A_Data.xlsx";
			FileInputStream fileInputStream = new FileInputStream(new File(filePath));
			@SuppressWarnings("resource")
            Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheet("UpCategory");

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				String url = row.getCell(5).getStringCellValue();
				String expectedTitle = row.getCell(4).getStringCellValue();
				driver.get(url);
				String actualTitle = driver.getTitle();

				if (!actualTitle.equals(expectedTitle)) {
					System.out.println("Dòng " + i + ": THẤT BẠI - Tiêu đề mong đợi: " + expectedTitle
							+ ", Tiêu đề thực tế: " + actualTitle);
					return false;
				}
			}
			workbook.close();
			fileInputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verifyLink(String link) {
		try {
			String filePath = "src/test/resources/data/AFF_A_Data.xlsx";
			FileInputStream fileInputStream = new FileInputStream(new File(filePath));
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheet("UpCategory");

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				String currentUrl = driver.getCurrentUrl();
				String expectedLink = row.getCell(5).getStringCellValue();

				System.out.println("Dòng " + i + ": URL hiện tại: " + currentUrl);

				if (!currentUrl.trim().equalsIgnoreCase(expectedLink.trim())) {
					System.out.println("Dòng " + i + ": THẤT BẠI - Link mong đợi: " + expectedLink + ", Link thực tế: "
							+ currentUrl);
					workbook.close();
					fileInputStream.close();
					return false;
				}
			}

			workbook.close();
			fileInputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
