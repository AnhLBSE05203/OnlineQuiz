package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Token;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.MailService;
import com.fpt.OnlineQuiz.service.TokenService;
import com.fpt.OnlineQuiz.utils.Constants;
import com.fpt.OnlineQuiz.utils.Utils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "admin_login_page";
    }

    @GetMapping("/forget_pass")
    public String forgetPass() {
        return "admin_forget_password";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "admin_dashboard";
    }

    @PostMapping("/forget_pass_action")
    public String processForgotPassword(Model model, HttpServletRequest request,
                                        @RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Account account = accountService.findAccountByEmail(email);
        if (account == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_ACCOUNT_NOT_FOUND);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append("/admin");
            sb.append("/forget_pass");
            return sb.toString();
        }
        String tokenString = RandomString.make(Constants.TOKEN_LENGTH);
        try {
            String resetPasswordLink = Utils.getSiteURL(request) + "/account/resetPassword?token=" + tokenString;
            mailService.sendResetPasswordEmail(email, resetPasswordLink);
            accountService.addToken(tokenString, email, Constants.TOKEN_TYPE_RESET_PASSWORD);
        } catch (UnsupportedEncodingException | MessagingException e) {
            //if fail to send mail, delete generated token
            Token token = tokenService.findByTokenString(tokenString);
            tokenService.deleteToken(token);
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_ERROR_SEND_EMAIL);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append("/admin");
            sb.append("/forget_pass");
            return sb.toString();
        }
        model.addAttribute(Constants.ATTRIBUTE_MESSAGE, "We have sent a reset password link to your email. Please check.");
        return "admin_forget_password";
    }

}
