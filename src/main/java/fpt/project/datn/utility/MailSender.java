package fpt.project.datn.utility;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailSender {

    private static final Session session;
    private MailSender() {
    }

    static {
        Properties props = new Properties() {{
            put("mail.smtp.auth", "true");
            put("mail.smtp.host", "smtp.gmail.com");
            put("mail.transport.protocol", "smtp");
            put("mail.smtp.port", "587");
            put("mail.smtp.starttls.enable", "true");
            put("mail.smtp.ssl.protocols", "TLSv1.2");
        }};

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("khanhquan69@gmail.com", "htusxgiewqgududw");
            }
        });
    }

    public static void sendMail(String subject, String content, String targetMail) {
        try {
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            message.setContent(content, "text/html");
            message.setFrom(new InternetAddress("khanhquan69@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(targetMail));
            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException("failed");
        }
    }
}
