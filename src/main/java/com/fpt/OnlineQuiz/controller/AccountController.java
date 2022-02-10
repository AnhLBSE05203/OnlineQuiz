package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.RegisterDTO;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Role;
import com.fpt.OnlineQuiz.model.Token;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.MailService;
import com.fpt.OnlineQuiz.service.RoleService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.scanner.Constant;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    @Autowired
    private RoleService roleService;
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
                                        @RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Account account = accountService.findAccountByEmail(email);
        if (account == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_ACCOUNT_NOT_FOUND);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_FORGOT_PASSWORD);
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
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_FORGOT_PASSWORD);
            return sb.toString();
        }
        model.addAttribute(Constants.ATTRIBUTE_MESSAGE, "We have sent a reset password link to your email. Please check.");
        return Constants.PAGE_FORGOT_PASSWORD;
    }

    /**
     * Display Reset Password Page
     * @param tokenString user's reset password token
     * @param model spring's model class
     * @return Reset Password Page html
     */
    @GetMapping(Constants.LINK_RESET_PASSWORD)
    public String showResetPasswordPage(@Param(value = "token") String tokenString, Model model) {
        Account account = accountService.findByToken(tokenString, Constants.TOKEN_TYPE_RESET_PASSWORD);
        model.addAttribute("token", tokenString);

        if (account == null) {
            model.addAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_INVALID_TOKEN);
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
        return Constants.PAGE_REGISTER;
    }

    @GetMapping(Constants.LINK_CHANGE_PASSWORD)
    public String showChangePasswordPage(Model model){
        //to do - create register page
        return Constants.PAGE_CHANGE_PASSWORD;
    }
    /**
     * Process Registration Submission
     * @param registerDTO register DTO
     * @param model spring's model class
     * @param request user's request
     * @return
     */
    @PostMapping(Constants.LINK_REGISTER)
    public String processRegistration(@ModelAttribute RegisterDTO registerDTO, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Account account = accountService.findAccountByEmail(registerDTO.getEmail());
        //add new account
        if(account != null){
            String message = "Email is already used!";
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, message);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_REGISTER);
            return sb.toString();
        }
        account = new Account();
        account.setEmail(registerDTO.getEmail().toLowerCase());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(registerDTO.getPassword());
        account.setPassword(encodedPassword);
        Date now = new Date();
        account.setCreatedTime(now);
        account.setUpdatedTime(now);
        account.setCreatedUserId(1);
        account.setUpdatedUserId(1);
        account.setGender(registerDTO.getGender());
        account.setPhone(registerDTO.getPhone());
        account.setFullName(registerDTO.getFullName());
        account.setCreatedUserId(1);
        account.setStatus(Constants.STATUS_UNCONFIRMED);
        Role role = roleService.findRoleByName(Constants.ROLE_USER);
        List<Role> roles= new ArrayList<>();
        roles.add(role);
        account.setRoles(roles);
        //create confirmation token
        String tokenString = RandomString.make(Constants.TOKEN_LENGTH);
        //add account
        accountService.addAccount(account);
        //add token
        accountService.addToken(tokenString, account.getEmail(), Constants.TOKEN_TYPE_CONFIRM_REGISTRATION);
        try {
            //send confirmation email
            String confirmLink = Utils.getSiteURL(request) + "/account/confirmRegistration?token=" + tokenString;
            mailService.sendConfirmRegistrationEmail(registerDTO.getEmail(), confirmLink);

        } catch (UnsupportedEncodingException | MessagingException e) {
            //if fail to send mail, delete generated token
            Token token = tokenService.findByTokenString(tokenString);
            tokenService.deleteToken(token);

            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_ERROR_SEND_EMAIL);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_REGISTER);
            return sb.toString();
        }
        model.addAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_REGISTER_SUCCESS);
        return Constants.PAGE_REGISTER;
    }

    /**
     * Process Registration Confirm
     * @param request user's request
     * @param model spring's model class
     * @return
     */
    @GetMapping(Constants.LINK_CONFIRM_REGISTRATION)
    public String processConfirmRegistration(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String tokenString = request.getParameter("token");
        Account account = accountService.findByToken(tokenString, Constants.TOKEN_TYPE_CONFIRM_REGISTRATION);
        String message = "";
        if (account == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_ACCOUNT_NOT_FOUND);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_REGISTER);
            return sb.toString();
        }
        Token token = tokenService.findByTokenString(tokenString);
        if(token == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_INVALID_TOKEN);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_REGISTER);
            return sb.toString();
        }
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
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_REGISTER);
            return sb.toString();
        }
        account.setStatus(Constants.STATUS_CONFIRMED);
        accountService.updateAccount(account);
        tokenService.deleteToken(token);
        redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_CONFIRM_SUCCESS);
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.LINK_REDIRECT);
        sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
        sb.append(Constants.LINK_REGISTER);
        return sb.toString();
    }
    /**
     * Process Reset Password Function
     * @param request user's request
     * @param model spring's model class
     * @return return to Reset Password Page
     */
    @PostMapping(Constants.LINK_RESET_PASSWORD)
    public String processResetPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String tokenString = request.getParameter("token");
        String password = request.getParameter("password");

        Account account = accountService.findByToken(tokenString, Constants.TOKEN_TYPE_RESET_PASSWORD);
        String message = "";
        if (account == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_ACCOUNT_NOT_FOUND);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_RESET_PASSWORD);
            return sb.toString();
        }
        Token token = tokenService.findByTokenString(tokenString);
        if(token == null) {
            redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_INVALID_TOKEN);
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.LINK_REDIRECT);
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_RESET_PASSWORD);
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
            sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
            sb.append(Constants.LINK_RESET_PASSWORD);
            return sb.toString();
        }
        accountService.resetPassword(account, password, token);
        redirectAttributes.addFlashAttribute(Constants.ATTRIBUTE_MESSAGE, Constants.MESSAGE_CHANGE_PASSWORD_SUCCESS);
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.LINK_REDIRECT);
        sb.append(Constants.LINK_ACCOUNT_CONTROLLER);
        sb.append(Constants.LINK_RESET_PASSWORD);
        return sb.toString();
    }
}
