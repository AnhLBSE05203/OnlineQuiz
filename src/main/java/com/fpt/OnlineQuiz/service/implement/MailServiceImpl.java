package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.service.MailService;
import com.fpt.OnlineQuiz.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.*;


@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendResetPasswordEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(Constants.MAIL_FROM, Constants.MAIL_FROM_NAME);
        helper.setTo(recipientEmail);

        String subject = Constants.MAIL_SUBJECT_RESET_PASSWORD;

        String content = Constants.getResetPasswordMailTemplate(link);

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);

    }

    @Override
    public void sendConfirmRegistrationEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(Constants.MAIL_FROM, Constants.MAIL_FROM_NAME);
        helper.setTo(recipientEmail);

        String subject = Constants.MAIL_SUBJECT_CONFIRM_REGISTRATION;

        String content = Constants.getConfirmRegistrationMailTemplate(link);

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}