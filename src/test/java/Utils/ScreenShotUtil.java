package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotUtil {
    public static String captureScreenshot(WebDriver driver, String testName, String testClass) throws IOException {
        File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destinationScreenshotPath = "screenshots/" + testClass + "_" + testName + "_" + System.currentTimeMillis() + ".png";
        Path destPath = Paths.get(destinationScreenshotPath);

        if (!Files.exists(destPath.getParent())) {
            Files.createDirectories(destPath.getParent());
        }

        Files.copy(srcScreenshot.toPath(), destPath);

        return destinationScreenshotPath;
    }
}
