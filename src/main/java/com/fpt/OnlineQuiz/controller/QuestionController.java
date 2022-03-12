package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.QuestionAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Answer;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.AnswerService;
import com.fpt.OnlineQuiz.service.LessonService;
import com.fpt.OnlineQuiz.service.QuestionService;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
    private LessonService lessonService;

    @GetMapping(path = "/create")
    String showCreateQuestionPage(ModelMap modelMap, HttpServletRequest request) {
        //todo - fix hardcode subjectId

//        modelMap.addAttribute("questionDTO", new QuestionDTO());
//        String subjectIdStr = request.getParameter("subjectId");
        int lessonId = Integer.parseInt(request.getParameter("lessonId"));
//        if (subjectIdStr != null && subjectIdStr.trim() != "") {
//            try{
//                subjectId = Integer.parseInt(subjectIdStr);
//            } catch (NumberFormatException e) {
//                //fail to parse, redirect back to wherever
//                return "redirect:/home";
//            }
//        }
        modelMap.addAttribute("lessonId", lessonId);
        return "create_question_page";
    }

    @PostMapping(path = "/addquestion")
    public String processCreateQuestion(ModelMap modelMap, HttpServletRequest request) {
        int lessonId = Integer.parseInt(request.getParameter("lessonId"));
        Lesson lesson = lessonService.getLessonByLessonId(lessonId);
        if (lesson == null) {
            //redirect to wherever if subject doesn't exist
            //use RedirectAttributes if wanting to pass an error message, maybe
            return "redirect:/home";
        }
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

        q.setLesson(lesson);
        questionService.addQuestion(q);
        answerService.addAnswers(answers);
        modelMap.addAttribute("message", "Add successful!");
        return "create_question_page";
    }

    @GetMapping(path = "/edit")
    String showEditQuestionPage(ModelMap modelMap, HttpServletRequest request) {
        String questionId = request.getParameter("questionId").trim();
        Question q = questionService.getQuestionByQuestionId(Integer.parseInt(questionId));
        // get subjectId through question, not param
        // then since subject is a proxy object (only contains Id), get subject through the id
        modelMap.addAttribute("question", q);
        List<Answer> answers = answerService.getAnswers(Integer.parseInt(questionId));
        modelMap.addAttribute("answers", answers);
        return "edit_question_page";
    }

    @PostMapping(path = "/editquestion")
    public String processEditQuestion(ModelMap modelMap, HttpServletRequest request) {
        //todo - edit not add
        int lessonId = Integer.parseInt(request.getParameter("lessonId"));
        Question q = new Question();
        //int questionId = Integer.parseInt(request.getParameter("questionId").trim());
        //Question q = questionService.getQuestionByQuestionId(questionId);
        q.setQuestion(request.getParameter("question").trim());
        q.setId(Integer.parseInt(request.getParameter("questionId")));
        int number = Integer.parseInt(request.getParameter("isAnswer"));
        //todo - either delete all Answers related to the Question then add new
//        answerService.deleteAnswerByQuestionId(q.getId());
        //todo - or edit the answers themselves
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
            a.setId(Integer.parseInt(request.getParameter("answer" + (i + 1) + "Id")));
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
        q.setExplain(request.getParameter("explain").trim());
        //todo - get subjectId through question instead
        // what if the Subject in @param is not the one that owns the Question in @param?
        Lesson lesson = lessonService.getLessonByLessonId(lessonId);
        q.setLesson(lesson);
        answerService.updateAnswers(answers);
        questionService.updateQuestion(q);
        modelMap.addAttribute("message", "Edit successful!");
        List<Question> questionList = questionService.getQuestionByLessonId(lesson.getId());
        modelMap.addAttribute("question_list", questionList);
        return "admin_list_question_page";
    }

    @GetMapping(path = "/list")
    String showQuestionListPage(ModelMap model) {
        //todo - fix hardcode subjectId
        List<Question> questionList = questionService.getQuestionByLessonId(1);
        model.addAttribute("question_list", questionList);
        return "admin_list_question_page";
    }

    @PostMapping(value = "/getQuestionsByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<QuestionAdminDTO> getQuestionsByPage(@RequestBody PagingRequest pagingRequest) {
        Page<QuestionAdminDTO> listQuestionDTO = questionService.getByPagingRequest(pagingRequest);
        return listQuestionDTO;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuestionAdminDTO getSubjectDetails(@PathVariable Integer id) {
        QuestionAdminDTO questionDTO = questionService.getQuestionDTOById(id);
        return questionDTO;
    }

    @GetMapping(path = "/delete")
    public String deleteQuestion(ModelMap modelMap, HttpServletRequest request) {
        try{
            int questionId = Integer.parseInt(request.getParameter("questionId"));
            //todo - get subjectId through Question
            // what if the Subject in @param is not the one that owns the Question in @param?
            Question question = questionService.getQuestionByQuestionId(questionId);
            answerService.deleteAnswerByQuestionId(questionId);

            questionService.deleteQuestion(questionId);
            List<Question> questionList = questionService.getQuestionByLessonId(question.getLesson().getId());
            modelMap.addAttribute("question_list", questionList);
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }
//        modelMap.addAttribute("message", "Delete successful!");
        return "admin_list_question_page";
    }
}
