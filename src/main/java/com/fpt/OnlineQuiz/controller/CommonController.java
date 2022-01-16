package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping(Constants.STRING_EMPTY)
public class CommonController {
    /**
     * Display Home Page
     * @param model spring's model class
     * @param principal User's authenticate/authorization principal
     * @return Home Page html
     */
    @GetMapping(value = {Constants.STRING_EMPTY, Constants.LINK_HOME})
    String homePage(Model model, Principal principal) {
        model.addAttribute("principal", principal);
        return Constants.PAGE_HOME;
    }
}
