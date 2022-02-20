package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.QuestionDTO;
import com.fpt.OnlineQuiz.model.Answer;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.AnswerService;
import com.fpt.OnlineQuiz.service.QuestionService;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    String showCreateQuestionPage(ModelMap modelMap) {
        modelMap.addAttribute("questionDTO", new QuestionDTO());

//        modelMap.addAttribute("subject", )
        return "create_question_page";
    }

    @PostMapping(path = "/addquestion")
    public String processCreateQuestion(ModelMap modelMap, HttpServletRequest request) {
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
        modelMap.addAttribute("message", "Add successful!");
        return "create_question_page";
    }

    @GetMapping(path = "/edit")
    String showEditQuestionPage(ModelMap modelMap) {
        //TODO Code Edit Question
        Question q = questionService.getQuestionByQuestionId(1);
        modelMap.addAttribute("questionDTO", new QuestionDTO());
        modelMap.addAttribute("question", q);
        return "edit_question_page";
    }

    @PostMapping(path = "/editquestion")
    public String processEditQuestion(@ModelAttribute QuestionDTO questionDTO, ModelMap modelMap, @RequestParam int ques_id) {
        Question q = new Question();
        Subject subject = subjectService.getSubjectById(1);
        q.setQuestion(questionDTO.getQuestion());
//        q.setAnswer(questionDTO.getAnswer());
        q.setExplain(questionDTO.getExplain());
        q.setSubject(subject);
//        q.setId(ques_id);
        questionService.updateQuestion(q);
        modelMap.addAttribute("message", "Update successful!");
        return "edit_question_page";
    }

    @GetMapping(path = "/listquestion")
    String showListQuestionPage(@Param(value = "subId") int subId, Model model, HttpServletRequest request) {
        List<Question> questionList = questionService.getQuesitonBySubjectId(1);
        Subject subject = subjectService.getSubjectById(subId);
        model.addAttribute("ques", questionList);
        model.addAttribute("sub", subject);
        return "question_list_page";
    }

}
