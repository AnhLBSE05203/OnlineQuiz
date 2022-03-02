package com.fpt.OnlineQuiz.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String STRING_EMPTY = "";
    public static final int NUMBER_ZERO = 0;
    public static final String REMEMBER_ME_KEY = "uniqueAndSecret";
    //account constants
    public static final int STATUS_ACCOUNT_CONFIRMED = 1;
    public static final int STATUS_ACCOUNT_UNCONFIRMED = 2;
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_EXPERT = "ROLE_EXPERT";
    public static final String ROLE_SALES = "ROLE_SALES";
    //db record common constants
    public static final int STATUS_DELETED = 0;
    public static final int STATUS_DEFAULT = 1;
    public static final int DEFAULT_CREATED_USER_ID = 1;
    public static final int DEFAULT_UPDATED_USER_ID = 1;
    //subject constants
    public static final Map<Integer, String> subjectStatusConversion = initSubjectStatusMap();
    public static final int STATUS_SUBJECT_DELETED = 0;
    public static final int STATUS_SUBJECT_ACTIVE = 1;
    public static final int STATUS_SUBJECT_INACTIVE = 2;

    //image constants
    public static final int DEFAULT_SUBJECT_IMAGE_ID = 1;
    public static final String DEFAULT_SUBJECT_IMAGE_SRC = "/img/gallery/featured1.png";
    //messages
    public static final String MESSAGE_INVALID_TOKEN = "Invalid Token!";
    public static final String MESSAGE_REGISTER_SUCCESS = "Register successful! Check email for confirmation link!";
    public static final String MESSAGE_CONFIRM_SUCCESS = "Registration Confirm Success!";
    public static final String MESSAGE_ERROR_SEND_EMAIL = "Error while sending email";
    public static final String MESSAGE_CHANGE_PASSWORD_SUCCESS = "You have successfully changed your password.";
    public static final String MESSAGE_ACCOUNT_NOT_FOUND = "Account not found!";
    public static final String MESSAGE_PASSWORD_INVALID = "Invalid Password! " +
            "Password need at least 1 upper case, " +
            "1 special character & length >= 8";
    public static final String MESSAGE_EMAIL_INVALID = "Invalid Email Input!";
    //token types
    public static final String TOKEN_TYPE_RESET_PASSWORD = "TOKEN_RESET_PASSWORD";
    public static final String TOKEN_TYPE_CONFIRM_REGISTRATION = "TOKEN_CONFIRM";

    //mail settings
    public static final String MAIL_FROM = "banhl.off@gmail.com";
    public static final String MAIL_FROM_NAME = "banhl.off";
    public static final String MAIL_PASSWORD = "Alo123!@#";
    public static final String MAIL_SUBJECT_RESET_PASSWORD = "Here's the link to reset your password";
    public static final String MAIL_SUBJECT_CONFIRM_REGISTRATION = "Here's the link to confirm your registration";

    //regex
    public static final String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String REGEX_PASSWORD = "^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[#?!@$%^&*\\-_]).{8,}$";
    //common links


    //links
    public static final String LINK_REDIRECT = "redirect:";
    public static final String LINK_ACCOUNT_CONTROLLER = "/account";
    public static final String LINK_LOGIN = "/login";
    public static final String LINK_ACCOUNT_LOGIN_PROCESS = "/account/login";
    public static final String LINK_ACCOUNT_LOGIN_FAILURE = "/account/login?error=true";
    public static final String LINK_ACCOUNT_LOGOUT = "/account/logout";
    public static final String LINK_REGISTER = "/register";
    public static final String LINK_HOME = "/home";
    public static final String LINK_ACCESS_DENIED = "/access_denied";
    public static final String LINK_FORGOT_PASSWORD = "/forgotPassword";
    public static final String LINK_RESET_PASSWORD = "/resetPassword";
    public static final String LINK_CHANGE_PASSWORD = "/changePassword";
    public static final String LINK_PROFILE = "/profile";
    public static final String LINK_EDIT_PROFILE = "/edit-profile";
    public static final String LINK_CONFIRM_REGISTRATION = "/confirmRegistration";

    //pages
    public static final String PAGE_LOGIN = "login_page";
    public static final String PAGE_REGISTER = "register_page";
    public static final String PAGE_HOME = "home_page";
    public static final String PAGE_ACCESS_DENIED = "access_denied_page";
    public static final String PAGE_FORGOT_PASSWORD = "forgot_password_page";
    public static final String PAGE_RESET_PASSWORD = "reset_password_page";
    public static final String PAGE_CHANGE_PASSWORD = "change_password_page";
    public static final String PAGE_PROFILE = "profile_page";
    public static final String PAGE_EDIT_PROFILE = "editProfile_page";


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
    public static final String LINK_ADMIN_LOGOUT = "/admin/logout";

    //links admin subject
    public static final String LINK_ADMIN_SUBJECT_CONTROLLER = "/admin/subject";
    public static final String LINK_ADMIN_SUBJECT_LIST = "/";
    public static final String LINK_ADMIN_SUBJECT_DETAIL = "/{id}";
    public static final String LINK_ADMIN_SUBJECT_GET_BY_NAME = "/getByName";
    public static final String LINK_ADMIN_SUBJECT_ADD = "/add";
    public static final String LINK_ADMIN_SUBJECT_DELETE = "/delete/{id}";
    public static final String LINK_ADMIN_SUBJECT_RECOVER = "/recover/{id}";
    public static final String LINK_ADMIN_SUBJECT_PROCESS_EDIT = "/edit";
    public static final String LINK_ADMIN_SUBJECT_GET_BY_PAGE = "/getSubjectsByPage";
    //admin subject page attributes
    public static final String ATTRIBUTE_SUBJECT_ADD_DTO = "subjectAddDTO";
    public static final String ATTRIBUTE_SUBJECT_EDIT_DTO = "subjectEditDTO";
    public static final String ATTRIBUTE_SUBJECT_STATUS_MAP = "statusMap";

    //pages admin
    public static final String PAGE_DASHBOARD = "admin_dashboard";
    public static final String PAGE_ADMIN_LOGIN = "admin_login_page";
    public static final String PAGE_ADMIN_FORGET_PASSWORD = "admin_forget_password";
    public static final String PAGE_ADMIN_SUBJECT_PAGE = "admin_subject_page";
    //sql path
    public static final String SQL_PATH_FIND_ACCOUNT_BY_TOKEN = "/static/sql/findAccountByToken.sql";
    public static final String SQL_PATH_FIND_ACCOUNT_BY_EMAIL = "/static/sql/findAccountByEmail.sql";
    public static final String SQL_PATH_FIND_ALL_ROLES = "/static/sql/findAllRoles.sql";
    public static final String SQL_PATH_FIND_TOKEN_BY_TOKEN_STRING = "/static/sql/findTokenByTokenString.sql";
    public static final String SQL_PATH_FIND_ROLE_BY_NAME = "/static/sql/findRoleByName.sql";
    public static final String SQL_PATH_GET_ALL_BLOG_LIST = "/static/sql/findAllBlog.sql";
    public static final String SQL_PATH_GET_DETAIL_BLOG = "/static/sql/findBlogById.sql";
    public static final String SQL_PATH_GET_BLOG_COUNT = "/static/sql/getBlogCount.sql";
    public static final String SQL_PATH_GET_FEATURED_COURSES = "/static/sql/getFeaturedCourses.sql";
    public static final String SQL_PATH_GET_ALL_SUBJECTS = "/static/sql/getAllSubjects.sql";
    public static final String SQL_PATH_GET_ALL_Lessons = "/static/sql/getAllLessons.sql";
    public static final String SQL_PATH_GET_SUBJECT_COUNT = "/static/sql/getSubjectCount.sql";
    public static final String SQL_PATH_GET_LESSON_COUNT = "/static/sql/getLessonCount.sql";

    public static final String SQL_PATH_GET_FEATURED_EXPERTS = "/static/sql/getFeaturedExperts.sql";
    public static final String SQL_PATH_FIND_SUBJECT_BY_ID = "/static/sql/findSubjectById.sql";
    public static final String SQL_PATH_GET_IMAGE_BY_ID = "/static/sql/getImageById.sql";
    //sql strings
    public static final String SQL_GET_COURSES_BY_ACCOUNT = "select a.courses from Account a where a.id =:id";
    public static final String SQL_GET_COURSE_BY_ID = "SELECT c FROM Course c WHERE c.id = :id";
    //home page constants
    public static final int HOME_PAGE_COURSE_NUMBER = 4;
    public static final int HOME_PAGE_EXPERT_NUMBER = 3;
    public static final int HOME_PAGE_SUBJECT_NUMBER = 8;
    public static final String HOME_PAGE_ATTRIBUTE_COURSE_FEATURED = "courseFeatured";
    public static final String HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED = "expertFeatured";
    public static final String HOME_PAGE_ATTRIBUTE_SUBJECT_FEATURED = "subjectFeatured";
    public static final int DESCRIPTION_TRIM_LENGTH = 147;
    public static final String DESCRIPTION_TRIM_TAIL = "...";
    //user subject list constants
    public static final int USER_SUBJECT_PAGE_SIZE = 6;

    private static Map<Integer, String> initSubjectStatusMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(STATUS_SUBJECT_DELETED, "Deleted");
        map.put(STATUS_SUBJECT_ACTIVE, "Active");
        map.put(STATUS_SUBJECT_INACTIVE, "Inactive");
        return Collections.unmodifiableMap(map);
    }

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
