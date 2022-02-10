package com.fpt.OnlineQuiz.utils;

public class Constants {
    public static final String STRING_EMPTY = "";
    //account constants
    public static final int STATUS_CONFIRMED = 1;
    public static final int STATUS_UNCONFIRMED = 0;
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_EXPERT = "ROLE_EXPERT";
    public static final String ROLE_SALES = "ROLE_SALES";
    //db record common constants
    public static final int STATUS_DELETED = 0;
    public static final int STATUS_DEFAULT = 1;
    //messages
    public static final String MESSAGE_INVALID_TOKEN = "Invalid Token!";
    public static final String MESSAGE_REGISTER_SUCCESS = "Register successful! Check email for confirmation link!";
    public static final String MESSAGE_CONFIRM_SUCCESS = "Registration Confirm Success!";
    public static final String MESSAGE_ERROR_SEND_EMAIL = "Error while sending email";
    public static final String MESSAGE_CHANGE_PASSWORD_SUCCESS = "You have successfully changed your password.";
    public static final String MESSAGE_ACCOUNT_NOT_FOUND = "Account not found!";
    //token types
    public static final String TOKEN_TYPE_RESET_PASSWORD = "TOKEN_RESET_PASSWORD";
    public static final String TOKEN_TYPE_CONFIRM_REGISTRATION = "TOKEN_CONFIRM";

    //mail settings
    public static final String MAIL_FROM = "banhl.off@gmail.com";
    public static final String MAIL_FROM_NAME = "banhl.off";
    public static final String MAIL_PASSWORD = "Alo123!@#";
    public static final String MAIL_SUBJECT_RESET_PASSWORD = "Here's the link to reset your password";
    public static final String MAIL_SUBJECT_CONFIRM_REGISTRATION = "Here's the link to confirm your registration";

    //links
    public static final String LINK_REDIRECT = "redirect:";
    public static final String LINK_ACCOUNT_CONTROLLER = "/account";
    public static final String LINK_LOGIN = "/login";
    public static final String LINK_LOGIN_PROCESS = "/login";
    public static final String LINK_LOGIN_FAILURE = "/login?error=true";
    public static final String LINK_LOGOUT = "/logout";
    public static final String LINK_REGISTER = "/register";
    public static final String LINK_HOME = "/home";
    public static final String LINK_ACCESS_DENIED = "/access_denied";
    public static final String LINK_FORGOT_PASSWORD = "/forgotPassword";
    public static final String LINK_RESET_PASSWORD = "/resetPassword";
    public static final String LINK_CHANGE_PASSWORD = "/changePassword";
    public static final String LINK_CONFIRM_REGISTRATION = "/confirmRegistration";
    //pages
    public static final String PAGE_LOGIN = "login_page";
    public static final String PAGE_REGISTER = "register_page";
    public static final String PAGE_HOME = "home_page";
    public static final String PAGE_ACCESS_DENIED = "access_denied_page";
    public static final String PAGE_FORGOT_PASSWORD = "forgot_password_page";
    public static final String PAGE_RESET_PASSWORD = "reset_password_page";
    public static final String PAGE_CHANGE_PASSWORD = "change_password";
    public static final String PAGE_ERROR = "error";

    //model attributes
    public static final String ATTRIBUTE_MESSAGE = "message";
    //token constants
    public static final int TOKEN_LENGTH = 30;


    //links admin
    public static final String LINK_ADMIN_CONTROLLER = "/admin";
    public static final String LINK_ADMIN_LOGIN = "/admin/login";
    public static final String LINK_ADMIN_LOGIN_PROCESS = "/admin/login";
    public static final String LINK_ADMIN_LOGIN_FAILURE = "/admin/login?error=true";
    public static final String LINK_ADMIN_DASHBOARD = "/admin/dashboard";

    //pages admin
    public static final String PAGE_DASHBOARD = "admin_dashboard";
    public static final String PAGE_ADMIN_LOGIN = "admin_login_page";
    public static final String PAGE_ADMIN_FORGET_PASSWORD = "admin_forget_password";

    //sql path
    public static final String SQL_PATH_FIND_ACCOUNT_BY_TOKEN = "/static/sql/findAccountByToken.sql";
    public static final String SQL_PATH_FIND_ACCOUNT_BY_EMAIL = "/static/sql/findAccountByEmail.sql";
    public static final String SQL_PATH_FIND_ALL_ROLES = "/static/sql/findAllRoles.sql";
    public static final String SQL_PATH_FIND_TOKEN_BY_TOKEN_STRING = "/static/sql/findTokenByTokenString.sql";
    public static final String SQL_PATH_FIND_ROLE_BY_NAME = "/static/sql/findRoleByName.sql";
    public static final String SQL_PATH_GET_ALL_BLOG_LIST = "/static/sql/findAllBlog.sql";
    public static final String SQL_PATH_GET_TOP_COURSES = "/static/sql/getTopCourses.sql";
    public static final String SQL_PATH_GET_FEATURED_EXPERTS = "/static/sql/getFeaturedExperts.sql";
    //home page constants
    public static final int HOME_PAGE_COURSE_NUMBER = 4;
    public static final int HOME_PAGE_EXPERT_NUMBER = 3;
    public static final String HOME_PAGE_ATTRIBUTE_COURSE_FEATURED = "courseFeatured";
    public static final String HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED = "expertFeatured";
    //mail templates
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
