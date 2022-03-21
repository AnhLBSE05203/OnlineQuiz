package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.AnswerDTO;
import com.fpt.OnlineQuiz.dto.LessonAdminDTO;
import com.fpt.OnlineQuiz.dto.QuestionDTO;
import com.fpt.OnlineQuiz.dto.QuizHandleDTO;
import com.fpt.OnlineQuiz.model.Answer;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.service.AnswerService;
import com.fpt.OnlineQuiz.service.LessonService;
import com.fpt.OnlineQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "quizhandelecontroller")
public class QuizHandleController {
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;
    @Autowired
    LessonService lessonService;
    @RequestMapping(value = "/quiz",method = RequestMethod.GET)
    String test(ModelMap modelMap, HttpServletRequest request) {
        String lessionid = request.getParameter("lessionid");
        if(lessionid == null){
            lessionid = "1";
        }
        int lessonid1 = Integer.parseInt(lessionid);
        List<QuestionDTO> questionList = questionService.getQuestionByLessonIdDTO(lessonid1);
        LessonAdminDTO lession = lessonService.getLessonAdminDTOById(lessonid1);
        List<AnswerDTO> answerList = answerService.getAllAnswerDTO();
        modelMap.addAttribute("listquestion",questionList);
        modelMap.addAttribute("listanswer",answerList);
        modelMap.addAttribute("lession",lession);
        return "quiz_handle_test";
    }

    @RequestMapping(value = "/quizpost",method = RequestMethod.POST)
    String getValue(HttpServletRequest request,ModelMap modelMap) {
        int score = 0;
        int lessionid = Integer.parseInt(request.getParameter("lessionid")) ;
       List<QuestionDTO>questionList = questionService.getQuestionByLessonIdDTO(lessionid);
        for (QuestionDTO questionID :questionList) {
            System.out.println(questionID.getId());
            String id = questionID.getId()+"";
            int answerIdCorrect = questionService.findAnswerIcCorrect(questionID.getId());
            if(answerIdCorrect == Integer.parseInt(request.getParameter(""+id))){
                score++;
            }
        }
        System.out.println(score);
        modelMap.addAttribute("score",score);
        modelMap.addAttribute("size",questionList.size());
        modelMap.addAttribute("lessionid",lessionid);
        return "Result";
    }
}
