package com.fpt.OnlineQuiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SubjectController {
    @GetMapping(path = "/mysubject")
    public String showMySubjectPage(ModelMap modelMap){
        return "my_subject_page";
    }
}
