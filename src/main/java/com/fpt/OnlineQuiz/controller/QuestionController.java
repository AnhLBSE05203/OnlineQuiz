package com.fpt.OnlineQuiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {
    @GetMapping(path = "/create")
    String showCreateQuestionPage(ModelMap modelMap){
        return "create_question_page";
    }
}
