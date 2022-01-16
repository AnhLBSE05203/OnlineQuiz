package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    private final MailService mailService;

    public AdminController(AccountService accountService, MailService mailService) {
        this.accountService = accountService;
        this.mailService = mailService;
    }

    @GetMapping("/login")
    String loginPage(Model model) {
        return "admin_login_page";
    }

    @GetMapping("/forget_pass")
    String forgetPass() {
        return "admin_forget_password";
    }


}
