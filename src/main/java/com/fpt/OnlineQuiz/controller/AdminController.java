package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.BlogService;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    public String blogPage(ModelMap modelMap) {
        ArrayList<Blog> listBlog = blogService.getAllBlog();
        modelMap.addAttribute("listBlog",listBlog);
        return "admin_blog_page";
    }

    @GetMapping("/blog/{id}")
    public Blog detailBlogPage(ModelMap modelMap, @PathVariable Integer id) {
        Blog blog = blogService.getDetailBlog(id);
        modelMap.addAttribute("detailBlog", blog);
        return blog;
    }

}
