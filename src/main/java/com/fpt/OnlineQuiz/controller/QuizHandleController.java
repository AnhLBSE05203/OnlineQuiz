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
        int lessid = 1;
        List<QuestionDTO> questionList = questionService.getQuestionByLessonIdDTO(lessid);
        LessonAdminDTO lession = lessonService.getLessonAdminDTOById(lessid);
        List<AnswerDTO> answerList = answerService.getAllAnswerDTO();
        modelMap.addAttribute("listquestion",questionList);
        modelMap.addAttribute("listanswer",answerList);
        modelMap.addAttribute("lession",lession);
        return "quiz_handle_test";
    }

    @RequestMapping(value = "/quizpost",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    QuizHandleDTO getValue(HttpServletRequest request) {
//            int qid = Integer.parseInt(request.getParameter("qid"));
//            System.out.println("qid:"+qid);
//            int aid = Integer.parseInt(request.getParameter("answer"));
//            System.out.println("aid"+aid);
            String lessid = request.getParameter("lessid");
            if(lessid == null){
                lessid = "1";
            }
            int lessionID = Integer.parseInt(lessid);
            System.out.println("lessid:"+lessid);
            List<QuestionDTO> questionList = questionService.getQuestionByLessonIdDTO(lessionID);
            System.out.println("qsize"+questionList.size());
            String count = request.getParameter("count");
            if (count == null) {
                count = "0";
            }
            int countNum = Integer.parseInt(count);
            System.out.println("count:"+count);
            countNum+=1;
            System.out.println("count:"+count);
            int question = questionList.get(countNum).getId();
            System.out.println("questionId:"+question);
            QuestionDTO questionName = questionList.get(countNum);
            List<AnswerDTO> answerList = answerService.getAnswersDTO(question);
            System.out.println("aListSize:"+answerList.size());
            QuizHandleDTO quizHandleDTO = new QuizHandleDTO(answerList,lessionID,countNum,questionName);
//            modelMap.addAttribute("listanswer",answerList);
//            modelMap.addAttribute("question",questionName);
//            modelMap.addAttribute("count",count);
//            modelMap.addAttribute("lessid",lessid);
//            if(count == questionList.size()-1){
//                return "result_box";
//            }
        return quizHandleDTO;
    }
}
