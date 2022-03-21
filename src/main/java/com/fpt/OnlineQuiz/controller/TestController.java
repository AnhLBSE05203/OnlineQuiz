package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(path = "")
    public void test(HttpServletRequest request){
        int lesson_id = Integer.parseInt(request.getParameter("lessonId"));
        int number = Integer.parseInt(request.getParameter("number"));
        List<Question> questionList = questionService.getNumberOfQuestionByLessonId(lesson_id, number);
        System.out.println(questionList);
    }
}
