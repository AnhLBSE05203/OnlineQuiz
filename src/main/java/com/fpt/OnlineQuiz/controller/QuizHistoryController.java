package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.*;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();

        List<QuizHistory> history = quizHistoryService.listQuizHistory(account.getId());
        model.addAttribute("history", history);
        return "quizHistory_page";
    }
}
