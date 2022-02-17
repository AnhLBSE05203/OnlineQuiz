package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(path = "/create")
    String showCreateQuestionPage(ModelMap modelMap){
        return "create_question_page";
    }
    @GetMapping(path = "/edit")
    String showEditQuestionPage(ModelMap modelMap){
        //TODO Code Edit Question
        return "edit_question_page";
    }
    @GetMapping(path = "/listquestion")
    String showListQuestionPage(ModelMap modelMap){
        List<Question> questionList = questionService.getQuesitonBySubjectId(1);
        return "question_list_page";
    }
}
