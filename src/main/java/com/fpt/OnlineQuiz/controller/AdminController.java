package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.service.SubjectService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

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
    public String loginPage(Model model) {
        return "admin_login_page";
    }

    @GetMapping("/forget_pass")
    public String forgetPass() {
        return "admin_forget_password";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "admin_dashboard";
    }

    @GetMapping("/blog")
    public String blogPage() {
        return "admin_blog_page";
    }

    @GetMapping(value = "/blog/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Blog> getAllBlog() {
        List<Blog> listBlog = blogService.getAllBlogAdmin();
        return listBlog;
    }

    @GetMapping("/blog/{id}")
    public Blog detailBlogPage(@PathVariable Integer id) {
        Blog blog = blogService.getDetailBlog(id);
        return blog;
    }

}
