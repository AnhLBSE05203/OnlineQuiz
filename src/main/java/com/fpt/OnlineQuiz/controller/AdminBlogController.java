package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.BlogAdminDto;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminBlogController {
    @Autowired
    private BlogService blogService;

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

    @PostMapping(value = "/blog/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<BlogAdminDto> getAllBlog(@RequestBody PagingRequest pagingRequest) {
        Page<BlogAdminDto> listBlog = blogService.getAllBlogAdmin(pagingRequest);
        return listBlog;
    }

    @GetMapping("/blog/{id}")
    public Blog detailBlogPage(@PathVariable Integer id) {
        Blog blog = blogService.getDetailBlog(id);
        return blog;
    }

}
