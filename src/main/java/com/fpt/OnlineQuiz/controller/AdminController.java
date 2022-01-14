package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    String loginPage(Model model) {
        return "login_page";
    }


}
