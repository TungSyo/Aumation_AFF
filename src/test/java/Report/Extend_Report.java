package Report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@SuppressWarnings("unused")
public class Extend_Report {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> threadLocalTest = new ThreadLocal<>();

    public static void startReport() throws IOException {
        if (extent == null) {
            String baseFileName = "extentReport";
            String fileExtension = ".html";
            String reportDirectory = System.getProperty("report.dir", "src/test/resources/reports/");
            File dir = new File(reportDirectory);
        
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("Không thể tạo thư mục báo cáo: " + reportDirectory);
            }

            String fileName = generateUniqueFileName(reportDirectory, baseFileName, fileExtension);

            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            String cssContent = readCssFile("report.css");
            htmlReporter.config().setCss(cssContent);

            System.out.println("Báo cáo đã được khởi tạo: " + fileName);
        }
    }

    public static void startTest(String testName, String category) throws IOException {
        if (extent == null) {
            startReport();
        }
        ExtentTest test = extent.createTest(testName).assignCategory(category);
        threadLocalTest.set(test);
        System.out.println("Bắt đầu test: " + testName + " - Category: " + category);
    }

    public static void addCategory(String category) {
        ExtentTest test = threadLocalTest.get();
        if (test != null) {
            test.assignCategory(category);
            System.out.println("Đã thêm category: " + category);
        } else {
            System.err.println("Test chưa được khởi tạo. Gọi startTest() trước khi thêm category.");
        }
    }

    private static void log(Status status, String message) {
        ExtentTest test = threadLocalTest.get();
        if (test != null) {
            test.log(status, message);
        } else {
            System.err.println("Test chưa được khởi tạo. Gọi startTest() trước khi ghi log.");
        }
    }

    public static void logPass(String message) {
        log(Status.PASS, message);
        System.out.println("PASS: " + message);
    }

    public static void logFail(String message) {
        log(Status.FAIL, message);
        System.out.println("FAIL: " + message);
    }

    public static void logInfo(String message) {
        log(Status.INFO, message);
        System.out.println("INFO: " + message);
    }


    public static void attachScreenshot(String screenshotPath) {
        try {
            ExtentTest test = threadLocalTest.get();
            if (test != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            } else {
                System.err.println("Test chưa được khởi tạo. Gọi startTest() trước khi đính kèm ảnh.");
            }
        } catch (Exception e) {
            System.err.println("Không thể đính kèm ảnh: " + e.getMessage());
        }
    }

    public static void endReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("Báo cáo đã được tạo thành công.");
        } else {
            System.err.println("Báo cáo chưa được khởi tạo. Không thể kết thúc.");
        }
    }

    public static void endTest() {
        ExtentTest test = threadLocalTest.get();
        if (test != null) {
            System.out.println("Đã kết thúc test case.");
            threadLocalTest.remove();
        } else {
            System.err.println("Không thể kết thúc test, test chưa được khởi tạo.");
        }
    }

    private static String generateUniqueFileName(String directory, String baseName, String extension) {
        String fileName;
        int counter = 1;
        do {
            fileName = directory + baseName + counter + extension;
            counter++;
        } while (new File(fileName).exists());
        return fileName;
    }

    private static String readCssFile(String cssPath) throws IOException {
        InputStream inputStream = Extend_Report.class.getClassLoader().getResourceAsStream(cssPath);
        if (inputStream == null) {
            throw new FileNotFoundException("Không tìm thấy file CSS trong resources: " + cssPath);
        }
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
}