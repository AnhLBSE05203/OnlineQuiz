package com.fpt.OnlineQuiz.service;


import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
    /**
     * Send Reset Password Mail
     * @param recipientEmail Recipient Email
     * @param link Reset Password link
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    void sendResetPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;

    /**
     * Send Confirm Registration Mail
     * @param recipientEmail Recipient Email
     * @param link Confirm Password Link
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    void sendConfirmRegistrationEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;

    void sendContactEmail(String recipientEmail, String name, String email, String topic, String content) throws MessagingException, UnsupportedEncodingException;
}
