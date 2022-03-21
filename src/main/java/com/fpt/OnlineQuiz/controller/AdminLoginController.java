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
import org.springframework.core.env.Environment;
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
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Environment env;

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
            String resetPasswordLink = Utils.getSiteURL(request) + "/admin/resetPassword?token=" + tokenString;
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

    @GetMapping("/resetPassword")
    public String showResetPasswordPage(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String tokenString = request.getParameter("token");
        Account account = null;

        if (tokenString != null) {
            account = accountService.findByToken(tokenString, Constants.TOKEN_TYPE_RESET_PASSWORD);
            model.addAttribute("token", tokenString);
        }
        if (account == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_INVALID_TOKEN);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append("/admin");
            sb.append("/forget_pass");
            return sb.toString();
        }

        return "admin_reset_password_page";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String tokenString = request.getParameter("token");
        String password = request.getParameter("password");

        Account account = accountService.findByToken(tokenString, Constants.TOKEN_TYPE_RESET_PASSWORD);
        String message = "";
        //check if account null
        if (account == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_ACCOUNT_NOT_FOUND);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append("/admin");
            sb.append("/forget_pass");
            return sb.toString();
        }
        //check if password is invalid
        if (!isPasswordValid(password)) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_PASSWORD_INVALID);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append("/admin");
            sb.append("/forget_pass");
            return sb.toString();
        }

        Token token = tokenService.findByTokenString(tokenString);

        //check if token is null
        if (token == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_INVALID_TOKEN);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append("/admin");
            sb.append("/forget_pass");
            return sb.toString();
        }

        //calculate time diff between current time & token creation
        Date now = new Date();
        long diff = now.getTime() - token.getCreatedDate().getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);

        //get token duration setting from application.properties
        String StrTokenDuration = env.getProperty("tokenDuration");
        int tokenDuration = Integer.parseInt(StrTokenDuration);
        if (seconds > tokenDuration) {
            tokenService.deleteToken(token);
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_INVALID_TOKEN);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append("/admin");
            sb.append("/forget_pass");
            return sb.toString();
        }

        //reset password
        accountService.resetPassword(account, password, token);
        redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_CHANGE_PASSWORD_SUCCESS);
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.LINK_REDIRECT);
        sb.append("/admin");
        sb.append("/forget_pass");
        return sb.toString();
    }

    public boolean isPasswordValid(String password) {
        return Pattern.matches(Constants.REGEX_PASSWORD, password);
    }

}
