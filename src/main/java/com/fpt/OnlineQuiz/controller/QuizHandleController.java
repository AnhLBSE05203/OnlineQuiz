package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Answer;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.service.AnswerService;
import com.fpt.OnlineQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "quizhandelecontroller")
public class QuizHandleController {
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;
    @RequestMapping(value = "/quiz",method = RequestMethod.GET)
    String test(ModelMap modelMap, HttpServletRequest request) {
        List<Question> questionList = questionService.getQuestionByLessonId(1);
        int question = questionList.get(0).getId();
        Question questionName = questionList.get(0);
        List<Answer> answerList = answerService.getAnswers(question);
        modelMap.addAttribute("listanswer",answerList);
        modelMap.addAttribute("question",questionName);
        modelMap.addAttribute("count",0);
        return "quiz_handle";
    }

    @RequestMapping(value = "/quizpost",method = RequestMethod.POST)
    String getValue(ModelMap modelMap, HttpServletRequest request) {
        try {
            int qid = Integer.parseInt(request.getParameter("qid"));
            List<Question> questionList = questionService.getQuestionByLessonId(qid);
            int count = Integer.parseInt(request.getParameter("count"));
            count+=1;
            int question = questionList.get(count).getId();
            Question questionName = questionList.get(count);
            List<Answer> answerList = answerService.getAnswers(question);
            int answerid = Integer.parseInt(request.getParameter("answer"));
            modelMap.addAttribute("listanswer",answerList);
            modelMap.addAttribute("question",questionName);
            modelMap.addAttribute("count",count);
            System.out.println(qid+"\t"+answerid);
            if(count == questionList.size()-1){
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "quiz_handle";
    }
}
