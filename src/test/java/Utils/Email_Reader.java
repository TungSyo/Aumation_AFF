package Utils;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Email_Reader {

    public static String readLatestEmail(String host, String username, String password) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);  // Sử dụng POP3
        properties.put("mail.pop3.port", "995");  // Port của POP3
        properties.put("mail.pop3.ssl.enable", "true");  // Đảm bảo SSL được bật
        properties.put("mail.pop3.starttls.enable", "true");  // Nếu cần StartTLS


        Session session = Session.getDefaultInstance(properties);
        Store store = null;
        Folder inbox = null;

        try {
            store = session.getStore("pop3s"); 
            store.connect(host, username, password);

            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            if (messages.length == 0) {
                System.out.println("⏳ Không có email mới.");
                return null;
            }

            MimeMessage latestMessage = (MimeMessage) messages[messages.length - 1];
            System.out.println("📧 Subject: " + latestMessage.getSubject());
            return getTextFromMessage(latestMessage);

        } catch (AuthenticationFailedException e) {
            System.err.println("❌ Lỗi xác thực tài khoản: " + e.getMessage());
            throw e;
        } catch (MessagingException e) {
            System.err.println("❌ Lỗi kết nối POP3: " + e.getMessage());
            throw e;
        } finally {
            if (inbox != null && inbox.isOpen()) inbox.close(false);
            if (store != null) store.close();
        }
    }

    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            return getTextFromMimeMultipart((MimeMultipart) message.getContent());
        }
        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent().toString());
            } else if (bodyPart.isMimeType("text/html")) {
                result.append((String) bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}
