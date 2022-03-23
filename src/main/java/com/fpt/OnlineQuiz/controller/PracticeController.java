package com.fpt.OnlineQuiz.controller;

import com.amazonaws.services.xray.model.Http;
import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDQuizHistoryAccountAddRepository;
import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.*;
import com.fpt.OnlineQuiz.service.*;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/practices")
public class PracticeController {

    private int idForAdd;
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
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LessonService lessonService;


    @GetMapping(value = "")
    public String practicelListPage(Model model) {
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
        } catch (Exception e) {
            return "redirect:/account/login";
        }
       /*Get all QuizHistory(1 quizHistory contain multiple questions) existed in user account.
       Same like quizlet,user add học phần (quizHistory) in their practices list,it wll display
       in here (practices list)
       */
        List<QuizHistory> quizHistories = quizHistoryService.getQuizByAccountAdd(account.getId());
//        Iterable<QuizPackageHistory> quizList = quizPackageService.getAllQuiz();
        model.addAttribute("quizHistory", quizHistories);
//        model.addAttribute("quizList", quizList);
//        model.addAttribute("account", account);

        return "practices_list_page";
    }
    @PostMapping(value = "")
    public String addPractice(Model model, HttpServletRequest request) {
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
        } catch (Exception e) {
            return "redirect:/account/login";
        }
       /*Get all QuizHistory(1 quizHistory contain multiple questions) existed in user account.
       Same like quizlet,user add học phần (quizHistory) in their practices list,it wll display
       in here (practices list)
       */
        QuizHistory quizHistory = quizHistoryService.findId(idForAdd);
        QuizHistoryAccountAdd quizHistoryAccountAdd = new QuizHistoryAccountAdd();
        quizHistoryAccountAdd.setAccount(account);
        quizHistoryAccountAdd.setQuizHistory(quizHistory);
        accountAddServices.addOwnerOrAdd(quizHistoryAccountAdd);

        List<QuizHistory> quizHistories = quizHistoryService.getQuizByAccountAdd(account.getId());
//        Iterable<QuizPackageHistory> quizList = quizPackageService.getAllQuiz();
        model.addAttribute("quizHistory", quizHistories);
//        model.addAttribute("quizList", quizList);
//        model.addAttribute("account", account);

        return "practices_list_page";
    }

    @GetMapping(value = "/detail")
    public String practiceDetailPage(Model model, @Param("id") int id) {
        idForAdd = id;
        Account account = new Account();
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            account = (Account) authentication.getPrincipal();
        } catch (Exception e) {
            return "redirect:/account/login";
        }
        List<Question> questions = questionService.getQuestionQHid(id);
        model.addAttribute("questions", questions);
        List<CourseFeaturedDTO> courseFeatured = courseService.getFeaturedCourses(Constants.HOME_PAGE_COURSE_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_COURSE_FEATURED, courseFeatured);
        List<ExpertFeaturedDTO> expertFeatured = expertService.getFeaturedExperts(Constants.HOME_PAGE_EXPERT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED, expertFeatured);
        List<Subject> subjectFeatured = subjectService.getFeaturedSubjects(Constants.HOME_PAGE_SUBJECT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_SUBJECT_FEATURED, subjectFeatured);
        List<QuizHistory> quizHistories = quizHistoryService.getQuizByAccountAdd(account.getId());
        QuizHistory checkExistHistory = quizHistoryService.checkExist(id,account.getId());
        //todo fix subject id
        int subjectId = 2;
        Subject s = subjectService.getSubjectById(2);
        List<Lesson> lessonList = lessonService.getAllLesson(subjectId);
        model.addAttribute("checkExist", checkExistHistory);
        model.addAttribute("quizHistory", quizHistories);
        model.addAttribute("lessonList", lessonList);
        model.addAttribute("subject", s);
        return "practices_detail_page";
    }
    @ResponseBody
    @GetMapping(value = "/count")
    int getCount(HttpServletRequest request){
        int lessonId = Integer.parseInt(request.getParameter("lessonId"));
        int size = questionService.countQuestionByLessonId(lessonId);
        return size;
    }

    @GetMapping(value = "/create")
    public String showCreateNewPackagePage(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Account account = (Account) authentication.getPrincipal();
        } catch (Exception e) {
            return "redirect:/account/login";
        }
        return "create-quiz-package";
    }

    @PostMapping(value = "/create")
    public String createNewPackagePage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Account account = new Account();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            account = (Account) authentication.getPrincipal();
        } catch (Exception e) {
            return "redirect:/account/login";
        }
        QuizHistory quizHistory = new QuizHistory();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        quizHistory.setName(request.getParameter("packageN"));
        quizHistory.setDes(request.getParameter("desQ"));
        quizHistory.setCreatedTime(new Date(formatter.format(date)));
        quizHistory.setAccount(account);
        quizHistoryService.addQuizPackage(quizHistory);

        QuizHistoryAccountAdd quizHistoryAccountAdd = new QuizHistoryAccountAdd();
        quizHistoryAccountAdd.setAccount(account);
        quizHistoryAccountAdd.setQuizHistory(quizHistory);
        accountAddServices.addOwnerOrAdd(quizHistoryAccountAdd);
        return "redirect:/practices";
    }

    @PostMapping(value = "/add")
    public String addQuiz(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Account account = new Account();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            account = (Account) authentication.getPrincipal();
        } catch (Exception e) {
            return "redirect:/account/login";
        }
        QuizHistory quizHistory = quizHistoryService.findId(idForAdd);
        Question question = new Question();
        question.setQuestion(request.getParameter("terms"));
        question.setAnswer(request.getParameter("def"));
        question.setQuizHistory(quizHistory);
        questionService.addQuestion(question);
        return "redirect:/practices/detail?id=" + idForAdd;
    }
    @GetMapping(value = "/search")
    public String practiceSearchPage(Model model, HttpServletRequest request) {
        List<CourseFeaturedDTO> courseFeatured = courseService.getFeaturedCourses(Constants.HOME_PAGE_COURSE_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_COURSE_FEATURED, courseFeatured);
        List<ExpertFeaturedDTO> expertFeatured = expertService.getFeaturedExperts(Constants.HOME_PAGE_EXPERT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED, expertFeatured);
        List<Subject> subjectFeatured = subjectService.getFeaturedSubjects(Constants.HOME_PAGE_SUBJECT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_SUBJECT_FEATURED, subjectFeatured);

        List<QuizHistory> quizHistories = quizHistoryService.finAllAccountId(request.getParameter("packName"));
        model.addAttribute("quizHistory", quizHistories);
        return "practices-search-page";
    }
}
