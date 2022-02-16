package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.BlogService;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@NoArgsConstructor
@AllArgsConstructor
public class AdminController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/login")
    String loginPage(Model model) {
        return "admin_login_page";
    }

    @GetMapping("/forget_pass")
    String forgetPass() {
        return "admin_forget_password";
    }

    @GetMapping("/dashboard")
    String dashboardPage() {
        return "admin_dashboard";
    }

    @GetMapping("/blog")
    String blogPage(ModelMap modelMap) {
        ArrayList<Blog> listBlog = blogService.getAllBlog();
        modelMap.addAttribute("listBlog",listBlog);
        return "admin_blog_page";
    }

    @GetMapping("/blog/{id}")
    String detailBlogPage(ModelMap modelMap, @PathVariable Integer id) {
        Blog blog = blogService.getDetailBlog(id);
        modelMap.addAttribute("detailBlog", blog);
        return "";
    }
    @GetMapping("/subject")
    String subjectPage(Model model) {
        List<Subject> listSubjects = subjectService.findAllSubjects();
        List<String> statuses = new ArrayList<>();
        for(Subject subject : listSubjects) {
            statuses.add(Constants.subjectStatusConversion.get(subject.getStatus()));
        }
        model.addAttribute("listSubjects", listSubjects);
        model.addAttribute("listStatus", statuses);
        return "admin_subject_page";
    }
}
