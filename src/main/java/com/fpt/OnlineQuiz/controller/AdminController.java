package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BlogService blogService;

    public AdminController(BlogService blogService) {
        this.blogService = blogService;
    }


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

}
