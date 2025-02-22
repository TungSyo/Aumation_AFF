package User.Search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class User_Search_Page {
	public WebDriver driver;
	
	@FindBy(xpath = "//a[.='Trang chủ']")
	public WebElement linkHomePage;

	@FindBy(xpath = "//a[.='Sản phẩm']")
	public WebElement linkProduct;

	@FindBy(xpath = "//input[@class='product__search-input ng-untouched ng-pristine ng-valid']")
	public WebElement txtSearch;

	@FindBy(xpath = "//button[@class='product__search-submit btn']")
	public WebElement btnSearch;
	
	@FindBy(xpath = "//li[@class='header__shop-icon-item header__shop-icon-item-cart']")
	public WebElement btnOrder;

	@FindBy(xpath = "//app-header/div[2]/div[1]/div[3]/div[3]/ul[1]/li[2]")
	public WebElement btnAccount;

	@FindBy(xpath = "///div[@class='product__search-category']")
	public WebElement btnCategory;
	
	public User_Search_Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
