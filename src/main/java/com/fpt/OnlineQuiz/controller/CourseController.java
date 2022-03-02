package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.CourseUserDTO;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/mycourses")
    public String showCoursePage(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            account = (Account) authentication.getPrincipal();
        } else {
            return "redirect:/home";
        }
        List<Course> courseList = courseService.getTop3Courses(account.getId());
        modelMap.addAttribute("my_course_list", courseList);
        return "my_courses_page";
    }

    @GetMapping(path = "/registration")
    public String showRegistrationPage(ModelMap modelMap) {
        //todo?
//        List<Course> courseList = courseService.getTop3Courses(3);
//        modelMap.addAttribute("my_course_list", courseList);
        return "registration_page";
    }


    @GetMapping(path = "/loadMoreCourse")
    public @ResponseBody
    void loadMore(
            @RequestParam("start") String startStr, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        int start = Integer.parseInt(startStr);
        //todo - fix hardcode accountId
        //Account account = ...
        int accountId = 3; //...
        List<Course> courses = courseService.getNext3Courses(accountId, start);
        if (courses.size() != 0) {
            for (Course c : courses) {
                out.println("<div class=\"col-lg-4 course\">\n" +
                        "                    <div class=\"properties properties2 mb-30\">\n" +
                        "                        <div class=\"properties__card\">\n" +
                        "                            <div class=\"properties__img overlay1\">\n" +
                        "                                <a href=\"#\"><img src=\"/img/gallery/featured2.png\" alt=\"\"></a>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"properties__caption\">\n" +
                        "                                <h3><a>" + c.getName() + "</a></h3>\n" +
                        "                                <p>" + c.getDescription() + "</p>\n" +
                        "                                <a href=\"#\" class=\"border-btn border-btn2\">Go to Course</a>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>");
            }
        }
    }

    @GetMapping(value = "/getCourseUserDTO/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CourseUserDTO getCourseUserDTO(@PathVariable Integer id) {
        CourseUserDTO courseUserDTO = courseService.getCourseUserDTO(id);
        return courseUserDTO;
    }
}
