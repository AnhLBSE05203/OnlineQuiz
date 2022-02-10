package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.service.MailService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.apache.commons.io.IOUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendResetPasswordEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        sendEmail(recipientEmail, Constants.MAIL_SUBJECT_RESET_PASSWORD, Constants.getResetPasswordMailTemplate(link), true);
    }

    @Override
    public void sendConfirmRegistrationEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        sendEmail(recipientEmail, Constants.MAIL_SUBJECT_CONFIRM_REGISTRATION, Constants.getConfirmRegistrationMailTemplate(link), true);
    }

    public void sendEmail(String toEmail, String title, String content, boolean isHtml) throws ServiceException {
        try {
            MimeMessage message = this.getMessageSetting(title, content, isHtml);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            Transport.send(message);
            System.out.println("Send mail successful to: " + toEmail);
        } catch (Exception ex) {
            File fileOut = new File(this.getClass().getClassLoader().getResource(".").getFile() + "log.txt");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(fileOut);
                DataOutputStream dos = new DataOutputStream(fos);
                try {
                    dos.writeChars(ex.toString());
                    fos.close();
                    dos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("Send mail error: " + toEmail);
            System.out.println(ex);
            throw new ServiceException("Send mail error: " + toEmail, ex);
        }
    }

    private MimeMessage getMessageSetting(String title, String content, boolean isHtml)
            throws MessagingException, UnsupportedEncodingException {

        // set config information to send mail
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");

        // Enable SSL
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//      props.put("mail.transport.protocol", "smtps");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.MAIL_FROM, Constants.MAIL_PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);
        String contentType = isHtml ? "text/html" : "text/plain";
        message.setHeader("Content-Type", contentType + "; charset=UTF-8");
        message.setFrom(new InternetAddress(Constants.MAIL_FROM, Constants.MAIL_FROM_NAME, StandardCharsets.UTF_8.name()));
        message.setSubject(title, "UTF-8");
        message.setContent(content, contentType + "; charset=utf-8");
        return message;
    }
}