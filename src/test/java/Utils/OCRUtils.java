package Utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;

public class OCRUtils {
    public static String extractTextFromImage(String imagePath) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata"); // Đường dẫn tới thư mục chứa trained data
        tesseract.setLanguage("vie"); // Nếu text là tiếng Việt, dùng "vie"; nếu là tiếng Anh, dùng "eng"

        try {
            return tesseract.doOCR(new File(imagePath));
        } catch (TesseractException e) {
            e.printStackTrace();
            return null;
        }
    }
}
