package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.RegisterDTO;
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
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(Constants.LINK_ACCOUNT_CONTROLLER)
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;
    @Autowired
    private Environment env;
    @Autowired
    private TokenService tokenService;
    /**
     * Display Login Page
     * @param model spring's model class
     * @return Login Page html
     */
    @GetMapping(Constants.LINK_LOGIN)
    String loginPage(Model model) {
        return Constants.PAGE_LOGIN;
    }

    /**
     * Display Forgot Password Page
     * @param model spring's model class
     * @return Forgot Page html
     */
    @GetMapping(Constants.LINK_FORGOT_PASSWORD)
    public String forgotPasswordPage(Model model) {
        return Constants.PAGE_FORGOT_PASSWORD;
    }

    /**
     * Process Forgot Password Function by sending User the Password Reset Token
     * @param model spring's model class
     * @param request user's request
     * @param email user's email
     * @return return the user to Forgot Password page
     */
    @PostMapping(Constants.LINK_FORGOT_PASSWORD)
    public String processForgotPassword(Model model, HttpServletRequest request,
                                @RequestParam("email") String email) {
        Account account = accountService.findAccountByEmail(email);
        if (account == null) {
            String message = "Account not found";
            model.addAttribute("message", message);
            return Constants.PAGE_FORGOT_PASSWORD;
        }
        String tokenString = RandomString.make(Constants.TOKEN_LENGTH);
        try {
            String resetPasswordLink = Utils.getSiteURL(request) + "/resetPassword?token=" + tokenString;
            mailService.sendResetPasswordEmail(email, resetPasswordLink);
            accountService.updateResetPasswordToken(tokenString, email);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
            //if fail to send mail, delete generated token
            Token token = tokenService.findByTokenString(tokenString);
            tokenService.deleteToken(token);
        }
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        return Constants.PAGE_FORGOT_PASSWORD;
    }

    /**
     * Display Reset Password Page
     * @param token user's reset password token
     * @param model spring's model class
     * @return Reset Password Page html
     */
    @GetMapping(Constants.LINK_RESET_PASSWORD)
    public String showResetPasswordPage(@Param(value = "token") String token, Model model) {
        Account account = accountService.findByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (account == null) {
            model.addAttribute("message", Constants.MESSAGE_INVALID_TOKEN);
            return Constants.PAGE_FORGOT_PASSWORD;
        }
        return Constants.PAGE_RESET_PASSWORD;
    }

    /**
     * Display Register Page
     * @param model spring's model class
     * @return
     */
    @GetMapping(Constants.LINK_REGISTER)
    public String showRegisterPage(Model model){
        //to do - create register page
        model.addAttribute("registerDTO", new RegisterDTO());
        model.addAttribute("message", Constants.STRING_EMPTY);
        return Constants.PAGE_REGISTER;
    }

    /**
     * Process Registration Submission
     * @param registerDTO register DTO
     * @param model spring's model class
     * @param request user's request
     * @return
     */
    @PostMapping(Constants.LINK_REGISTER)
    public String processRegistration(@ModelAttribute RegisterDTO registerDTO, Model model, HttpServletRequest request) {
        Account account = accountService.findAccountByEmail(registerDTO.getEmail());
        //add new account
        if(account != null){
            String message = "Email is already used!";
            model.addAttribute("message", message);
            return Constants.PAGE_REGISTER;
        }
        account = new Account();
        account.setEmail(registerDTO.getEmail().toLowerCase());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(registerDTO.getPassword());
        account.setPassword(encodedPassword);
        Date now = new Date();
        account.setCreatedTime(now);
        account.setUpdatedTime(now);
        account.setGender(registerDTO.getGender());
        account.setPhone(registerDTO.getPhone());
        account.setFullName(registerDTO.getFullName());
        account.setCreatedUserId(1);
        accountService.addAccount(account);

        //create confirmation token
        String tokenString = RandomString.make(30);
        try {
            //send confirmation email
            String confirmLink = Utils.getSiteURL(request) + "/confirmRegistration?token=" + tokenString;
            mailService.sendConfirmRegistrationEmail(registerDTO.getEmail(), confirmLink);
            accountService.updateConfirmToken(tokenString, registerDTO.getEmail());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
            //if fail to send mail, delete generated token
            Token token = tokenService.findByTokenString(tokenString);
            tokenService.deleteToken(token);
        }
        return Constants.PAGE_HOME;
    }

    /**
     * Process Registration Confirm
     * @param request user's request
     * @param model spring's model class
     * @return
     */
    @GetMapping(Constants.LINK_CONFIRM_REGISTRATION)
    public String processConfirmRegistration(HttpServletRequest request, Model model) {
        String tokenString = request.getParameter("token");
        Account account = accountService.findByConfirmToken(tokenString);
        String message = "";
        if (account == null) {
            message = "Account not found";
            model.addAttribute("message", message);
            return Constants.PAGE_ERROR;
        }
        Token token = tokenService.findByTokenString(tokenString);
        if(token == null) {
            model.addAttribute("message", Constants.MESSAGE_INVALID_TOKEN);
            return Constants.PAGE_ERROR;
        }
        Date now = new Date();
        long diff = now.getTime() - token.getCreatedDate().getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);

        //get token duration setting from application.properties
        String StrTokenDuration = env.getProperty("tokenDuration");
        int tokenDuration = Integer.parseInt(StrTokenDuration);
        if (seconds > tokenDuration) {
            model.addAttribute("message", Constants.MESSAGE_INVALID_TOKEN);
            return Constants.PAGE_ERROR;
        }
        account.setStatus(Constants.STATUS_CONFIRMED);
        accountService.updateAccount(account);
        tokenService.deleteToken(token);
        return Constants.PAGE_HOME;
    }
    /**
     * Process Reset Password Function
     * @param request user's request
     * @param model spring's model class
     * @return return to Reset Password Page
     */
    @PostMapping(Constants.LINK_RESET_PASSWORD)
    public String processResetPassword(HttpServletRequest request, Model model) {
        String tokenString = request.getParameter("token");
        String password = request.getParameter("password");

        Account account = accountService.findByResetPasswordToken(tokenString);
        model.addAttribute("title", "Reset your password");
        List<Token> tokens = account.getTokens();
        Token resetToken = new Token();
        for(Token t : tokens){
            if(t.getTokenType().equals(Constants.TOKEN_TYPE_RESET_PASSWORD)){
                resetToken = t;
            }
        }
        //calculate time diff between current time & token creation
        Date now = new Date();
        long diff = now.getTime() - resetToken.getCreatedDate().getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);

        //get token duration setting from application.properties
        String StrTokenDuration = env.getProperty("tokenDuration");
        int tokenDuration = Integer.parseInt(StrTokenDuration);
        if (account == null || seconds > tokenDuration) {
            model.addAttribute("message", "Invalid Token");
            return Constants.PAGE_RESET_PASSWORD;
        } else {
            accountService.updatePassword(account, password, resetToken);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return Constants.PAGE_RESET_PASSWORD;
    }
}
