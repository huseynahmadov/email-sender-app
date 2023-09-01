package io.emailapp.service.concrete;

import io.emailapp.service.abstraction.EmailService;
import io.emailapp.util.EmailUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author Huseyn Ahmadov
 * @version 1.0
 * @since 01.09.2023
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
public class EmailServiceHandler implements EmailService {

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    @Value("${spring.mail.verify.host}")
    String host;

    @Value("${spring.mail.username}")
    String fromEmail;

    final JavaMailSender mailSender;

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtil.getEmailMessage(name, host, token));
            mailSender.send(message);
        } catch (Exception exception) {
            log.error("Error occurred when mail sending: {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }

    }

    @Override
    @Async
    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try {
            var message = getMimeMessage();
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1); // this means that this mail is important mail
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setText(EmailUtil.getEmailMessage(name, host, token));

            //Add attachments
            FileSystemResource file1 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image1.jpg"));
            FileSystemResource file2 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image2.jpg"));
            FileSystemResource file3 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/file.pdf"));
            helper.addAttachment(file1.getFilename(), file1);
            helper.addAttachment(file2.getFilename(), file2);
            helper.addAttachment(file3.getFilename(), file3);

            mailSender.send(message);
        } catch (Exception exception) {
            log.error("Error occurred when mail sending: {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedImages(String name, String to, String token) {
        try {
            var message = getMimeMessage();
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1); // this means that this mail is important mail
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setText(EmailUtil.getEmailMessage(name, host, token));

            //Add attachments
            FileSystemResource file1 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image1.jpg"));
            FileSystemResource file2 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image2.jpg"));
            FileSystemResource file3 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/file.pdf"));
            helper.addInline(getContentId(file1.getFilename()), file1);
            helper.addInline(getContentId(file2.getFilename()), file2);
            helper.addInline(getContentId(file3.getFilename()), file3);

            mailSender.send(message);
        } catch (Exception exception) {
            log.error("Error occurred when mail sending: {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlEmail(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {

    }

    private MimeMessage getMimeMessage() {
        return mailSender.createMimeMessage();
    }

    private String getContentId(String fileName) {
        return "<" + fileName + ">";
    }

}
