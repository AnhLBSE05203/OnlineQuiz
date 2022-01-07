package com.fpt.OnlineQuiz.service;


import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
}
