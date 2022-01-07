package com.fpt.OnlineQuiz.service;


import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
    /**
     * Send Mail
     * @param recipientEmail Recipient Email
     * @param link Reset Password link
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    void sendResetPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
}
