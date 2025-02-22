package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE_PATH = "src/test/resources/config.properties";

    static {
        loadPropertiesFile();
    }

    private static void loadPropertiesFile() {
        File configFile = new File(PROPERTIES_FILE_PATH);
        if (!configFile.exists()) {
            System.err.println("⚠️ Warning: Configuration file not found: " + PROPERTIES_FILE_PATH);
            return;
        }
        try (FileInputStream fileIn = new FileInputStream(configFile)) {
            properties.load(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to load configuration file: " + PROPERTIES_FILE_PATH);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key, "").trim(); 
    }

    public static void setProperty(String key, String value) {
        try (FileOutputStream fileOut = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            properties.setProperty(key, value);
            properties.store(fileOut, null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to save configuration property: " + key);
        }
        loadPropertiesFile();
    }

    public static void reloadProperties() {
        loadPropertiesFile();
    }

    public static String getLink() throws IOException {
        String link = getProperty("url_2");
        if (link.isEmpty()) {
            throw new IOException("❌ URL is not found in the properties file.");
        }
        return link;
    }

    public static String getEmail() throws IOException {
        String email = getProperty("username");
        if (email.isEmpty()) {
            throw new IOException("❌ Email is not found in the properties file.");
        }
        return email;
    }

    public static String getPassword() throws IOException {
        String password = getProperty("password");
        if (password.isEmpty()) {
            throw new IOException("❌ Password is not found in the properties file.");
        }
        return password;
    }

    public static String getAppUrl() {
        return getProperty("app.url");
    }
}
