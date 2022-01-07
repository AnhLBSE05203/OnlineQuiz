package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.MailService;
import com.fpt.OnlineQuiz.utils.Utils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    String loginPage(Model model) {
        return "login_page";
    }

    @GetMapping(value = {"", "/home"})
    String homePage(Model model, Principal principal) {
        model.addAttribute("principal", principal);
        return "home_page";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(Model model, Principal principal) {

        return "access_denied_page";
    }
    @GetMapping("/manage")
    public String ManagePage(Model model, Principal principal) {

        return "manage_page";
    }

    @GetMapping("/forgotPassword")
    public String forgotPasswordPage(Model model) {
        return "forgot_password_page";
    }
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
            mailService.sendEmail(email, resetPasswordLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        return "forgot_password_page";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Account account = accountService.findByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (account == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        return "reset_password_page";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Account account = accountService.findByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (account == null) {
            model.addAttribute("message", "Invalid Token");
            return "reset_password_page";
        } else {
            accountService.updatePassword(account, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "reset_password_page";
    }
}
