package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.MailService;
import com.fpt.OnlineQuiz.utils.Utils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;
    @Autowired
    private Environment env;
    /**
     * Display Login Page
     * @param model spring's model class
     * @return Login Page html
     */
    @GetMapping("/login")
    String loginPage(Model model) {
        return "login_page";
    }

    /**
     * Display Home Page
     * @param model spring's model class
     * @param principal User's authenticate/authorization principal
     * @return Home Page html
     */
    @GetMapping(value = {"", "/home"})
    String homePage(Model model, Principal principal) {
        model.addAttribute("principal", principal);
        return "home_page";
    }

    /**
     * Display Access Denied Page when User's not authorized
     * @param model spring's model class
     * @param principal User's authenticate/authorization principal
     * @return Access Denied Page html
     */
    @GetMapping("/access_denied")
    public String accessDeniedPage(Model model, Principal principal) {

        return "access_denied_page";
    }

    /**
     * Display Forgot Password Page
     * @param model spring's model class
     * @return Forgot Page html
     */
    @GetMapping("/forgotPassword")
    public String forgotPasswordPage(Model model) {
        return "forgot_password_page";
    }

    /**
     * Process Forgot Password Function by sending User the Password Reset Token
     * @param model spring's model class
     * @param request user's request
     * @param email user's email
     * @return return the user to Forgot Password page
     */
    @PostMapping("/forgotPassword")
    public String processForgotPassword(Model model, HttpServletRequest request,
                                @RequestParam("email") String email) {
        Account account = accountService.findAccountByEmail(email);
        if (account == null) {
            String message = "Account not found";
            model.addAttribute("message", message);
            return "forgot_password_page";
        }
        String token = RandomString.make(30);
        try {
            accountService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utils.getSiteURL(request) + "/resetPassword?token=" + token;
            mailService.sendResetPasswordEmail(email, resetPasswordLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        return "forgot_password_page";
    }

    /**
     * Display Reset Password Page
     * @param token user's reset password token
     * @param model spring's model class
     * @return Reset Password Page html
     */
    @GetMapping("/resetPassword")
    public String showResetPasswordPage(@Param(value = "token") String token, Model model) {
        Account account = accountService.findByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (account == null) {
            model.addAttribute("message", "Invalid Token");
            return "forgot_password_page";
        }
        return "reset_password_page";
    }

    /**
     * Process Reset Password Function
     * @param request user's request
     * @param model spring's model class
     * @return return to Reset Password Page
     */
    @PostMapping("/resetPassword")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Account account = accountService.findByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        //calculate time diff between current time & token creation
        Date now = new Date();
        long diff = now.getTime() - account.getTokenCreatedTime().getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);

        //get token duration setting from application.properties
        String StrTokenDuration = env.getProperty("tokenDuration");
        int tokenDuration = Integer.parseInt(StrTokenDuration);
        if (account == null || seconds > tokenDuration) {
            model.addAttribute("message", "Invalid Token");
            return "reset_password_page";
        } else {
            accountService.updatePassword(account, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "reset_password_page";
    }
}
