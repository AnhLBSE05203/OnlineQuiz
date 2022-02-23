package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.QuestionAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Answer;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.AnswerService;
import com.fpt.OnlineQuiz.service.QuestionService;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping(path = "/create")
    String showCreateQuestionPage(ModelMap modelMap, HttpServletRequest request) {
//        modelMap.addAttribute("questionDTO", new QuestionDTO());
//        String subjectIdStr = request.getParameter("subjectId");
        int subjectId = 1;
//        if (subjectIdStr != null && subjectIdStr.trim() != "") {
//            try{
//                subjectId = Integer.parseInt(subjectIdStr);
//            } catch (NumberFormatException e) {
//                //fail to parse, redirect back to wherever
//                return "redirect:/home";
//            }
//        }
        modelMap.addAttribute("subjectId", subjectId);
        return "create_question_page";
    }

    @PostMapping(path = "/addquestion")
    public String processCreateQuestion(ModelMap modelMap, HttpServletRequest request) {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        Question q = new Question();
        q.setQuestion(request.getParameter("question").trim());
        int number = Integer.parseInt(request.getParameter("isAnswer"));
        String answer1 = request.getParameter("answer1").trim();
        String answer2 = request.getParameter("answer2").trim();
        String answer3 = request.getParameter("answer3").trim();
        String answer4 = request.getParameter("answer4").trim();
        List<String> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);
        answerList.add(answer4);
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Answer a = new Answer();
            a.setAnswer(answerList.get(i));
            if (number == (i + 1)) {
                q.setAnswer(a.getAnswer());
                a.setCorrect(true);
            } else {
                a.setCorrect(false);
            }
            a.setQuestion(q);
            answers.add(a);
        }
        q.setAnswers(answers);
        q.setExplain(request.getParameter("explain"));
        Subject subject = subjectService.getSubjectById(subjectId);
        if (subject == null) {
            //redirect to wherever if subject doesn't exist
            //use RedirectAttributes if wanting to pass an error message, maybe
            return "redirect:/home";
        }
        q.setSubject(subject);
        questionService.addQuestion(q);

        answerService.addAnswers(answers);
        modelMap.addAttribute("message", "Add successful!");
        return "create_question_page";
    }

    @GetMapping(path = "/edit")
    String showEditQuestionPage(ModelMap modelMap) {
        //TODO Code Edit Question
        Question q = questionService.getQuestionByQuestionId(31);
        modelMap.addAttribute("question", q);
        List<Answer> answers = answerService.getAnswers(31);
        modelMap.addAttribute("answer", answers);
        return "edit_question_page";
    }

    @PostMapping(path = "/editquestion")
    public String processEditQuestion(ModelMap modelMap, HttpServletRequest request) {
        Question q = new Question();
        q.setQuestion(request.getParameter("question").trim());
        int number = Integer.parseInt(request.getParameter("isAnswer"));
        String answer1 = request.getParameter("answer1").trim();
        String answer2 = request.getParameter("answer2").trim();
        String answer3 = request.getParameter("answer3").trim();
        String answer4 = request.getParameter("answer4").trim();
        List<String> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);
        answerList.add(answer4);
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Answer a = new Answer();
            a.setAnswer(answerList.get(i));
            if (number == (i + 1)) {
                q.setAnswer(a.getAnswer());
                a.setCorrect(true);
            } else {
                a.setCorrect(false);
            }
            a.setQuestion(q);
            answers.add(a);
        }
        q.setAnswers(answers);
        q.setExplain(request.getParameter("explain"));
        Subject subject = subjectService.getSubjectById(1);
        q.setSubject(subject);
        questionService.addQuestion(q);

        answerService.addAnswers(answers);
        modelMap.addAttribute("message", "Edit successful!");
        return "edit_question_page";
    }

//    @GetMapping(path = "/listquestion")
//    String showListQuestionPage(@Param(value = "subId") int subId, Model model, HttpServletRequest request) {
//        List<Question> questionList = questionService.getQuesitonBySubjectId(1);
//        Subject subject = subjectService.getSubjectById(subId);
//        model.addAttribute("ques", questionList);
//        model.addAttribute("sub", subject);
//        return "question_list_page";
//    }

    @GetMapping(path = "/list")
    String showQuestionListPage(ModelMap model){
        List<Question> questionList = questionService.getQuesitonBySubjectId(1);
        Subject subject = subjectService.getSubjectById(1);
        model.addAttribute("question_list", questionList);
        model.addAttribute("sub", subject);
        return "admin_list_question_page";
    }
    @PostMapping(value = "/getQuestionsByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<QuestionAdminDTO> getQuestionsByPage(@RequestBody PagingRequest pagingRequest) {
        Page<QuestionAdminDTO> listQuestionDTO = questionService.getByPagingRequest(pagingRequest);
        return listQuestionDTO;
    }
}
