package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Expert;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.service.ExpertService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(Constants.STRING_EMPTY)
public class CommonController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExpertService expertService;
    /**
     * Display Home Page
     * @param model spring's model class
     * @param principal User's authenticate/authorization principal
     * @return Home Page html
     */
    @GetMapping(value = {Constants.STRING_EMPTY, Constants.LINK_HOME})
    String homePage(Model model, Principal principal) {
        model.addAttribute("principal", principal);

        List<Course> courseFeatured = courseService.getTopCourses(Constants.HOME_PAGE_COURSE_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_COURSE_FEATURED, courseFeatured);
        List<ExpertFeaturedDTO> expertFeatured = expertService.getTopExperts(Constants.HOME_PAGE_EXPERT_NUMBER);
        model.addAttribute(Constants.HOME_PAGE_ATTRIBUTE_EXPERT_FEATURED, expertFeatured);
        System.out.println();
        return Constants.PAGE_HOME;
    }

    /**
     * Display Access Denied Page when User's not authorized
     * @param model spring's model class
     * @param principal User's authenticate/authorization principal
     * @return Access Denied Page html
     */
    @GetMapping(Constants.LINK_ACCESS_DENIED)
    public String accessDeniedPage(Model model, Principal principal) {

        return Constants.PAGE_ACCESS_DENIED;
    }

}
