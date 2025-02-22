package User.SCart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SCart_Page {
     public WebDriver driver;

     @FindBy(xpath = "")
     public WebElement abc;
     
     @FindBy(xpath = "//a[.='Trang chủ']")
	public WebElement linkHomePage;


     public SCart_Page(WebDriver driver){
          this.driver = driver;
          PageFactory.initElements(driver, this);
     }
}
