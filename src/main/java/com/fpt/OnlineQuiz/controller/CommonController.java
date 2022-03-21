package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.service.ExpertService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(Constants.LINK_COMMON_CONTROLLER)
public class CommonController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "/getPrincipal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Account getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            account = (Account) authentication.getPrincipal();
            System.out.println(account.getEmail());
        }
        return account;
    }

    /**
     * Display Home Page
     *
     * @param model spring's model class
     * @return Home Page html
     */
    @GetMapping(value = {Constants.STRING_EMPTY, Constants.LINK_HOME})
    String homePage(Model model) {

        List<CourseFeaturedDTO> courseFeatured = courseService.getFeaturedCourses(Constants.HOME_PAGE_COURSE_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_COURSE_FEATURED, courseFeatured);
        List<ExpertFeaturedDTO> expertFeatured = expertService.getFeaturedExperts(Constants.HOME_PAGE_EXPERT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED, expertFeatured);
        List<Subject> subjectFeatured = subjectService.getFeaturedSubjects(Constants.HOME_PAGE_SUBJECT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_SUBJECT_FEATURED, subjectFeatured);
        return Constants.PAGE_HOME;
    }

    /**
     * Display Access Denied Page when User's not authorized
     *
     * @param model     spring's model class
     * @param principal User's authenticate/authorization principal
     * @return Access Denied Page html
     */
    @GetMapping(Constants.LINK_ACCESS_DENIED)
    public String accessDeniedPage(Model model, Principal principal) {

        return Constants.PAGE_ACCESS_DENIED;
    }

}
