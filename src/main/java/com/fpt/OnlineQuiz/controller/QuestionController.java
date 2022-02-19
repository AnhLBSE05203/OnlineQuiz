package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.QuestionService;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping(path = "/create")
    String showCreateQuestionPage(ModelMap modelMap){
        return "create_question_page";
    }
    @GetMapping(path = "/edit")
    String showEditQuestionPage(ModelMap modelMap){
        //TODO Code Edit Question
        return "edit_question_page";
    }
    //
    @GetMapping(path = "/listquestion")
    String showListQuestionPage(@Param(value = "subId") int subId, Model model, HttpServletRequest request) {
        List<Question> questionList = questionService.getQuesitonBySubjectId(1);
        Subject subject = subjectService.getSubjectById(subId);
        model.addAttribute("ques",questionList);
        model.addAttribute("sub",subject);
        return "question_list_page";
    }
    //create 7
}
