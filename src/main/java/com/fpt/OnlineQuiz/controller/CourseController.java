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

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/courselist")
    public String showCoursesList(ModelMap modelMap, HttpServletRequest request){
        int subjectId = Integer.parseInt(request.getParameter("subId"));
        List<Course> courses = courseService.getCoursesTop3BySubjectId(subjectId);
        modelMap.addAttribute("list", courses);
        return "course_list_page";
    }
    @GetMapping(path = "/loadMore")
    public @ResponseBody
    void loadMoreCourse(@RequestParam("subjectId") String subjectId,
            @RequestParam("start") String startStr, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        int start = Integer.parseInt(startStr);
        int subId = Integer.parseInt(subjectId);
        List<Course> courses = courseService.getNext3Courses(subId, start);
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

    @GetMapping(path = "/delete")
    public String deleteRegistrationCourse(ModelMap modelMap, HttpServletRequest request) {
        int courseId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        List<Course> cart = (List<Course>) session.getAttribute("cart");
        double total = Double.parseDouble(session.getAttribute("total").toString());
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == courseId) {
                total -= cart.get(i).getPrice();
                System.out.println("Total = " + total);
                cart.remove(i);
                break;
            }
            if (cart.size() == 0) {
                total = 0;
            }
        }
        session.setAttribute("cart", cart);
        session.setAttribute("total", String.format("%.02f", total));
        return "registration_page";
    }

    @GetMapping(path = "/registration")
    public String registrationCourse(ModelMap modelMap, HttpServletRequest request) {
        //check login account
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            account = (Account) authentication.getPrincipal();
        } else {
            return "redirect:/account/login";
        }
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") == null) {
            List<Course> cart = new ArrayList<>();
            Course c = courseService.getById(courseId);
            cart.add(c);
            session.setAttribute("cart", cart);
            session.setAttribute("total", String.format("%.02f", c.getPrice()));
        } else {
            List<Course> cart = (List<Course>) session.getAttribute("cart");
            double total = Double.parseDouble(session.getAttribute("total").toString());
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).getId() == courseId) {
                    modelMap.addAttribute("message", "This course is existed!");
                    return "registration_page";
                }
            }
            //Check course is registed in db before add to course list
            List<Course> courses = courseService.getCoursesRegistration(account.getId());
            boolean isExisted = false;
            for (int j = 0; j < courses.size(); j++) {
                if (courses.get(j).getId() == courseId) {
                    modelMap.addAttribute("message", "You have already registed this course!");
                    return "registration_page";
                }
            }
            Course c = courseService.getById(courseId);
            cart.add(c);
            total += c.getPrice();

            session.setAttribute("cart", cart);
            session.setAttribute("total", String.format("%.02f", total));
        }
        return "registration_page";
    }

    @GetMapping(path = "/showRegistration")
    public String showRegistrationPage(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") == null) {
            modelMap.addAttribute("message", "Empty");
        }
        return "registration_page";
    }

    @GetMapping(path = "/buyCourse")
    public String buyCourse(ModelMap modelMap, HttpServletRequest request) {
        //check login account
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            account = (Account) authentication.getPrincipal();
        } else {
            return "redirect:/account/login";
        }
        //check course list in session is null or not
        HttpSession session = request.getSession();
        if (session == null) {
            modelMap.addAttribute("message", "Empty");
        } else {
            // todo: Add course to Account_Course table
            List<Course> cart = (List<Course>) session.getAttribute("cart");
            courseService.addCourseRegistration(cart, account.getId());
            session.removeAttribute("cart");
            modelMap.addAttribute("message", "Buy Successful");
        }

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            account = (Account) authentication.getPrincipal();
        }
        List<Course> courses = courseService.getNext3Courses(account.getId(), start);
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
