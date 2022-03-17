package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.BlogAdminDTO;
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

    @PostMapping(value = "/edit")
    public String editBlog(@ModelAttribute("blogEditDTO")BlogAdminDTO blogAdminDTO) {
        Blog blog = blogService.getDetailBlog(blogAdminDTO.getId());
        Utils.copyNonNullProperties(blogAdminDTO, blog);
        blogService.updateBlog(blog);
        return Constants.LINK_REDIRECT + "/admin/blog";
    }

    @PostMapping(value = "/add")
    public String addBlog(@ModelAttribute("blogAddDTO") BlogAdminDTO blogAdminDTO) {
        Blog blog = new Blog();
        Utils.copyNonNullProperties(blogAdminDTO, blog);
        blogService.addBlog(blog);
        return Constants.LINK_REDIRECT + "/admin/blog";
    }

    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<BlogAdminDTO> getAllBlog(@RequestBody PagingRequest pagingRequest) {
        Page<BlogAdminDTO> listBlog = blogService.getAllBlogAdmin(pagingRequest);
        return listBlog;
    }

    @GetMapping(value = "/view/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BlogAdminDTO detailBlogPage(@PathVariable Integer id) {
        Blog blog = blogService.getDetailBlog(id);
        BlogAdminDTO blogAdminDTO = new BlogAdminDTO();
        Utils.copyNonNullProperties(blog, blogAdminDTO);
        return blogAdminDTO;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteBlog(@PathVariable Integer id) {
        Blog blog = blogService.getDetailBlog(id);
        blog.setStatus(Constants.STATUS_DELETED);
        blogService.updateBlog(blog);
        return Constants.LINK_REDIRECT + "/admin/blog";
    }

}
