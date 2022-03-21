package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.*;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @Autowired
    private SubjectService subjectService;
    @Autowired
    QuizHistoryService quizHistoryService;
    @Autowired
    QuizHistoryAccountAddServices accountAddServices;
    @Autowired
    QuestionService questionService;

    @Autowired
    private CourseService courseService;
    @Autowired
    private ExpertService expertService;

    @GetMapping("/list")
    public String showLessonListPage(@Param(value = "subId") int subId, Model model, HttpServletRequest request) {
        List<CourseFeaturedDTO> courseFeatured = courseService.getFeaturedCourses(Constants.HOME_PAGE_COURSE_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_COURSE_FEATURED, courseFeatured);
        List<ExpertFeaturedDTO> expertFeatured = expertService.getFeaturedExperts(Constants.HOME_PAGE_EXPERT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED, expertFeatured);
        List<Subject> subjectFeatured = subjectService.getFeaturedSubjects(Constants.HOME_PAGE_SUBJECT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_SUBJECT_FEATURED, subjectFeatured);

        List<Lesson> list = lessonService.getAllLesson(subId);
        Optional<Subject> subject = subjectService.getSubject(subId);

        model.addAttribute("subject", subject);
        model.addAttribute("lessons", list);
        model.addAttribute("subId", subId);
        return "listLesson_page";
    }

    @GetMapping("/detail")
    public String showLessonDetailPage(@Param(value = "lesId") int lesId, Model model, HttpServletRequest request) {
        Optional<Lesson> lesson = lessonService.getLessonById(lesId);
//        Optional<Subject> subject = subjectService.getSubject(subId);
//
//        model.addAttribute("subject", subject);
        model.addAttribute("lesson", lesson);
        return "lessonDetail_page";
    }
}
