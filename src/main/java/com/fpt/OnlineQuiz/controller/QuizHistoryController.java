package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.*;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/quiz-history")
public class QuizHistoryController {

    @Autowired
    QuizHistoryService quizHistoryService;

    @Autowired
    QuestionService questionService;

    @Autowired
    private CourseService courseService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "")
    public String quizHistoryPage(Model model) {
        List<CourseFeaturedDTO> courseFeatured = courseService.getFeaturedCourses(Constants.HOME_PAGE_COURSE_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_COURSE_FEATURED, courseFeatured);
        List<ExpertFeaturedDTO> expertFeatured = expertService.getFeaturedExperts(Constants.HOME_PAGE_EXPERT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED, expertFeatured);
        List<Subject> subjectFeatured = subjectService.getFeaturedSubjects(Constants.HOME_PAGE_SUBJECT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_SUBJECT_FEATURED, subjectFeatured);
        Account account = new Account();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            account = (Account) authentication.getPrincipal();
        }catch (Exception e){
            return "redirect:/account/login";
        }


        List<QuizHistory> history = quizHistoryService.listQuizHistory(account.getId());
        model.addAttribute("history", history);
        return "quiz-history-page";
    }
    @GetMapping(value = "detail")
    public String quizHistorydetail(Model model,  @Param("id") int id) {
        List<CourseFeaturedDTO> courseFeatured = courseService.getFeaturedCourses(Constants.HOME_PAGE_COURSE_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_COURSE_FEATURED, courseFeatured);
        List<ExpertFeaturedDTO> expertFeatured = expertService.getFeaturedExperts(Constants.HOME_PAGE_EXPERT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED, expertFeatured);
        List<Subject> subjectFeatured = subjectService.getFeaturedSubjects(Constants.HOME_PAGE_SUBJECT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_SUBJECT_FEATURED, subjectFeatured);
        Account account = new Account();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            account = (Account) authentication.getPrincipal();
        }catch (Exception e){
            return "redirect:/account/login";
        }

        List<Question> question = questionService.getQuestionQuizHistoryId(id);
        List<QuizHistory> history = quizHistoryService.listQuizHistory(account.getId());
        model.addAttribute("history", history);
        model.addAttribute("question", question);

        return "history-detail-page";
    }
}
