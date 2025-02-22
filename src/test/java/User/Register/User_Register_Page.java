package User.Register;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class User_Register_Page {
	public static Object UserName;

	@SuppressWarnings("unused")
	
	private WebDriver driver;

	@FindBy(xpath = "//input[@placeholder='Tên người dùng']")
	public WebElement txtName;

	@FindBy(xpath = "//input[@formcontrolname='citizenIdentification']")
	public WebElement txtCMND;

	@FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
	public WebElement txtSdt;
	
	@FindBy(xpath = "//input[@formcontrolname='password']")
	public WebElement txtPass;

	@FindBy(xpath = "//input[@formcontrolname='email']")
	public WebElement txtEmail;

	
	@FindBy(xpath = "//input[@formcontrolname='referralCode']")
	public WebElement txtMgt;
	
	// Tỉnh - Huyện - Xã
	@FindBy(xpath = "//input[@formcontrolname='cityName']")
	public WebElement txtCity;

	@FindBy(xpath = "//input[@formcontrolname='districtName']")
	public WebElement txtDistrict;
	
	@FindBy(xpath = "//input[@formcontrolname='wardName']")
	public WebElement txtWard;

	@FindBy(xpath = "//input[@formcontrolname='address']")
	public WebElement txtLocation;
	
	@FindBy(xpath = "//input[@formcontrolname='personalTaxCode']")
	public WebElement txtMst;

	@FindBy(xpath = "//input[@formcontrolname='dateOfBirth']")
	public WebElement txtDate;
	
	@FindBy(xpath = "//input[@formcontrolname='bankName']")
	public WebElement txtBank;
	
	@FindBy(xpath = "//input[@formcontrolname='bankAccountNumber']")
	public WebElement txtStk;
	
	@FindBy(xpath = "//input[@formcontrolname='isActive']")
	public WebElement cbDongy;
	
	@FindBy(xpath = "//button[@class='button-submit']")
	public WebElement btnTaotk;

	@FindBy(xpath = "//div[@class='otp-inputs']/input[1]")
	public WebElement txtOtp;

	@FindBy(xpath = "//a[@class='resend-link'][contains(.,'Gửi lại')]")
	public WebElement btnResend;

	@FindBy(xpath = "//button[contains(@class,'verify-button')]")
	public WebElement btnXacnhan;

	
	public User_Register_Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
