package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/registration")
    public String showRegistrationPage(ModelMap modelMap){
        List<Course> courseList = courseService.getCoursesRegistration(3);
        modelMap.addAttribute("courselist", courseList);
        return "registration_page";
    }
}
