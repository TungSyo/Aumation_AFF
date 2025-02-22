//package Admin.Login;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//
//public class LoginPage {
//	public WebDriver driver;
//	
//	@FindBy(xpath = "//input[@id='email1']")
//	public WebElement txtUser;
//
//	@FindBy(xpath = "//input[@placeholder='Nhập mật khẩu']")
//	public WebElement txtPass;
//
//	@FindBy(xpath = "//button[@type='submit']")
//	public WebElement btnLogin;
//
//	@FindBy(xpath = ".p-checkbox-icon")
//	public WebElement cbNhomatkhau;
//
//	public LoginPage(WebDriver driver) {
//		this.driver = driver;
//		PageFactory.initElements(driver, this);
//	}
//}
