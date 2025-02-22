package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Base_Page {
	// -------------------------------------------------------------------------
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

	@FindBy(xpath = "//span[.='Đăng ký']")
	public WebElement linkRegister;

	@FindBy(xpath = "//span[.='Quên mật khẩu']")
	public WebElement LinkForgotPassword;

	
	// Account Admin
	@FindBy(xpath = "//input[@id='email1']")
	public WebElement txtAUser;

	@FindBy(xpath = "//input[@placeholder='Nhập mật khẩu']")
	public WebElement txtAPass;

	@FindBy(xpath = "//button[@type='submit']")
	public WebElement btnALogin;

	@FindBy(xpath = ".p-checkbox-icon")
	public WebElement cbNhomatkhau;
	
	// Account Userr
	@FindBy(xpath = "//div[@class='field-group user_name']//input[@id='user']")
	public WebElement txtUUser;

	@FindBy(xpath = "//div[@class='field-group']//input[@id='user']")
	public WebElement txtUPass;

	@FindBy(xpath = "//button[@class='button-submit']")
	public WebElement btnULogin;
	
	// Menu admin
	// -------------------------------------------------------------------------
	@FindBy(xpath = "//span[@class='layout-menuitem-text ng-tns-c183498709-7']")
	public WebElement menuOverview;
	
	// -------------------------------------------------------------------------
	@FindBy(xpath = "//a[.='Sản phẩm']")
	public WebElement menuProduct;

	@FindBy(xpath = "//a[.='Danh sách sản phẩm']")
	public WebElement menuListProduct;
	
	@FindBy(xpath = "//a[.='Danh mục sản phẩm']")
	public WebElement menuListCategory;
	
	@FindBy(xpath = "//a[.='Thương hiệu']")
	public WebElement menuListBrand;
	
	// -------------------------------------------------------------------------
	@FindBy(xpath = "//a[.='Quản trị hệ thống']")
	public WebElement menuAdminSystem;
	
	@FindBy(xpath = "//a[.='Nhóm quyền']")
	public WebElement menuAuthor;

	@FindBy(xpath = "//a[.='Quản lý tài khoản']")
	public WebElement menuAccount;
	
	@FindBy(xpath = "//a[.='Tài khoản người dùng']")
	public WebElement menuInfoAccount;

	// -------------------------------------------------------------------------
	@FindBy(xpath = "//a[.='Hệ thống']")
	public WebElement menuSystem;
	
	@FindBy(xpath = "//a[@href='/admin/pages/tree-binary/show']")
	public WebElement menuLevelAccount;

	// -------------------------------------------------------------------------
	@FindBy(xpath = "//a[.='Rút điểm thưởng']")
	public WebElement menuWithdraw;

	@FindBy(xpath = "//a[.='Danh sách rút điểm thưởng']")
	public WebElement menuListPoint;
	
	@FindBy(xpath = "//a[.='Hoa hồng điểm thưởng']")
	public WebElement menuCommission;
	 
	// -------------------------------------------------------------------------
	@FindBy(xpath = "//a[.='Đơn hàng']")
	public WebElement menuOrder;
	
	@FindBy(xpath = "//a[.='Tạo đơn hàng']")
	public WebElement menuCreatOrder;
	
	@FindBy(xpath = "//a[.='Lịch sử đặt hàng']")
	public WebElement menuHistoryOrder;
	
	@FindBy(xpath = "//a[.='Danh sách đơn hàng']")
	public WebElement menuListOrder;

	// ------------------------------------------------------------------------- 
	@FindBy(xpath = "//a[.='Quản trị nội dung']")
	public WebElement menuContent;
	
	@FindBy(xpath = "//a[.='Quản lý tin tức']")
	public WebElement menuNews;

	@FindBy(xpath = "(//span[contains(@class,'button__label')])[5]")
	public WebElement btnLoginEmail;

	// Google
	@FindBy(xpath = "//input[contains(@type,'email')]")
	public WebElement txtEmailGG;

	@FindBy(xpath = "//input[contains(@type,'password')]")
	public WebElement txtPassGG;

	@FindBy(xpath = "//span[@jsname='V67aGc'][contains(.,'Next')]")
	public WebElement btnNext;

	@FindBy(xpath = "(//i[contains(@class,'fa fa-power-off')])[1]")
	public WebElement btnLogout;

	@FindBy(xpath = "//div[@class='AsY17b'][contains(.,'Sử dụng một tài khoản khác')]")
	public WebElement btnNewAccount;

	@FindBy(xpath = "//div[@class='TN bzz aHS-aHO'][contains(.,'Tất cả thư')]")
	public  WebElement btnAllEmail;

	@FindBy(xpath = "(//span[@class='ait'])[1]")
	public  WebElement btnAddView;
	
	@FindBy(xpath = "//div[@class='D E G-atb PY']//div[@class='G-tF']/div[4]")
	public  WebElement btnReload;

	@FindBy(xpath = "(//div[contains(@class,'asa')])[12]")
	public  WebElement btnDeleteMail;

	public Base_Page(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
