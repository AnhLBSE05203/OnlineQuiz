package com.fpt.OnlineQuiz.utils;

public class Constants {
    public static final String STRING_EMPTY = "";

    //account constants
    public static final int STATUS_CONFIRMED = 1;
    public static final int STATUS_UNCONFIRMED = 0;
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;

    //error messages
    public static final String MESSAGE_INVALID_TOKEN = "Invalid Token!";

    //token types
    public static final String TOKEN_TYPE_RESET_PASSWORD = "TOKEN_RESET_PASSWORD";
    public static final String TOKEN_TYPE_CONFIRM_REGISTRATION = "TOKEN_CONFIRM";

    //mail settings
    public static final String MAIL_FROM = "daugiafpt@gmail.com";
    public static final String MAIL_FROM_NAME = "daugiafpt";
    public static final String MAIL_SUBJECT_RESET_PASSWORD = "Here's the link to reset your password";
    public static final String MAIL_SUBJECT_CONFIRM_REGISTRATION = "Here's the link to confirm your registration";

    //links
    public static final String LINK_ACCOUNT_CONTROLLER = "/account";
    public static final String LINK_LOGIN = "/login";
    public static final String LINK_LOGIN_PROCESS = "/account/login";
    public static final String LINK_LOGIN_FAILURE = "/account/login?error=true";
    public static final String LINK_LOGOUT = "/logout";
    public static final String LINK_REGISTER = "/register";
    public static final String LINK_HOME = "/home";
    public static final String LINK_ACCESS_DENIED = "/access_denied";
    public static final String LINK_FORGOT_PASSWORD = "/forgotPassword";
    public static final String LINK_RESET_PASSWORD = "/resetPassword";
    public static final String LINK_CONFIRM_REGISTRATION = "/confirmRegistration";

    //pages
    public static final String PAGE_LOGIN = "login_page";
    public static final String PAGE_REGISTER = "register_page";
    public static final String PAGE_HOME = "home_page";
    public static final String PAGE_ACCESS_DENIED = "access_denied_page";
    public static final String PAGE_FORGOT_PASSWORD = "forgot_password_page";
    public static final String PAGE_RESET_PASSWORD = "reset_password_page";
    public static final String PAGE_ERROR = "error";

    public static final int TOKEN_LENGTH = 30;

    public static String getResetPasswordMailTemplate(String link) {
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        return content;
    }
    public static String getConfirmRegistrationMailTemplate(String link) {
        String content = "<p>Hello,</p>"
                + "<p>An account was registered with this email</p>"
                + "<p>Click the link below to confirm registration</p>"
                + "<p><a href=\"" + link + "\">Confirm my registration</a></p>"
                + "<br>"
                + "<p>Ignore this email if it was not you </p>";
        return content;
    }
}
