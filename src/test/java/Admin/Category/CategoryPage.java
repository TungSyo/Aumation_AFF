package Admin.Category;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CategoryPage {
	public WebDriver driver;
	
	@FindBy(xpath = "//button[@class='p-element p-ripple p-button p-component']")
	public WebElement btnAddCate;

	@FindBy(xpath = "//button[@class='p-element p-ripple p-button-rounded p-button-success mr-2 p-button p-component p-button-icon-only']")
	public WebElement btnUpCate;

	@FindBy(xpath = "//input[@class='form-control ng-untouched ng-pristine ng-valid']")
	public WebElement boxSearch;

	@FindBy(xpath = "//button[@class='p-element p-ripple buttonfilter p-button p-component']")
	public WebElement btnLoc;

	@FindBy(xpath = "//button[@class='p-ripple p-element p-paginator-prev p-paginator-element p-link p-disabled']")
	public WebElement btnPageT;
	
	@FindBy(xpath = "//button[@class='p-ripple p-element p-paginator-next p-paginator-element p-link p-disabled']")
	public WebElement btnPageS;
	
	@FindBy(xpath = "//input[@id='name']")
	public WebElement txtNameCate;
	
	@FindBy(xpath = "//div[@class='w-full p-treeselect p-component p-inputwrapper']")
	public WebElement dropCategory;
	
	@FindBy(xpath = "//textarea[@id='description']")
	public WebElement txtDescribe;
	
	@FindBy(xpath = "//button[@class='p-element p-ripple p-button-success buttoncloses p-button p-component']")
	public WebElement btnCancel;
	
	@FindBy(xpath = "//span[.='Lưu']")
	public WebElement btnSave;
	
	public CategoryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}