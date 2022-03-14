package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.BlogAdminDTO;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import com.fpt.OnlineQuiz.utils.Constants;
import com.fpt.OnlineQuiz.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/blog")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping(value = {"", "/"})
    public String blogPage(Model model) {
        model.addAttribute("blogEditDTO", new BlogAdminDTO());
        model.addAttribute("blogAddDTO", new BlogAdminDTO());
        return "admin_blog_page";
    }

    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<BlogAdminDTO> getAllBlog(@RequestBody PagingRequest pagingRequest) {
        Page<BlogAdminDTO> listBlog = blogService.getAllBlogAdmin(pagingRequest);
        return listBlog;
    }

    @GetMapping("/{id}")
    public Blog detailBlogPage(@PathVariable Integer id) {
        Blog blog = blogService.getDetailBlog(id);
        return blog;
    }

}
