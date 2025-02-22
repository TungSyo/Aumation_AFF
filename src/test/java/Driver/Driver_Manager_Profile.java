package Driver;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@SuppressWarnings("unused")
public class Driver_Manager_Profile {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> threadLocalWait = new ThreadLocal<>();

    public static void initDriver(Browser_Type browser) {
        WebDriver driver;
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().clearDriverCache();
                // WebDriverManager.chromedriver().driverVersion("133.0.6943.127").setup();
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\dactu\\.cache\\selenium\\chromedriver\\win64\\133.0.6943.127\\chromedriver.exe");
    
                ChromeOptions chromeOptions = new ChromeOptions();
                String userDataDir = "C:\\Users\\dactu\\AppData\\Local\\Google\\Chrome\\User Data";
                String profileName = "Profile 5";
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");

                chromeOptions.addArguments("--user-data-dir=" + userDataDir);
                chromeOptions.addArguments("--profile-directory=" + profileName);
                
    
                driver = new ChromeDriver(chromeOptions);
                break;
    
            case EDGE:
                WebDriverManager.edgedriver().clearDriverCache();
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;
    
            case FIREFOX:
                WebDriverManager.firefoxdriver().clearDriverCache();
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
    
            case CHROMIUM:
                WebDriverManager.chromiumdriver().clearDriverCache();
                ChromeOptions chromiumOptions = new ChromeOptions();
                chromiumOptions.addArguments(" --disable-extensions");
                chromiumOptions.addArguments("--disable-gpu");
                chromiumOptions.addArguments("--no-sandbox");
                chromiumOptions.addArguments("--disable-dev-shm-usage");
    
                driver = new ChromeDriver(chromiumOptions);
                break;
    
            default:
                throw new IllegalArgumentException("Trình duyệt không được hỗ trợ: " + browser);
        }
    
        driver.manage().window().maximize();  // Tự động tối đa hóa cửa sổ khi khởi tạo
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    
        threadLocalDriver.set(driver);
        threadLocalWait.set(new WebDriverWait(driver, Duration.ofSeconds(10))); // Khởi tạo WebDriverWait

    }
    

    public static WebDriver getDriver() {
        WebDriver driver = threadLocalDriver.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver chưa được khởi tạo. Gọi initDriver() trước.");
        }
        return driver;
    }


    public static WebDriverWait getWait() {
        WebDriverWait wait = threadLocalWait.get();
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait chưa được khởi tạo. Gọi initDriver() trước.");
        }
        return wait;
    }
    
    public static void quitDriver() {
        WebDriver driverInstance = threadLocalDriver.get();
        if (driverInstance != null) {
            driverInstance.quit();
            threadLocalDriver.remove();
        }
    }

    public static void quitAllDrivers() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("✅ Đã đóng tất cả trình duyệt.");
        }
    }

}