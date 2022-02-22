package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/blogcontroller")
//http:localhost:8080/blogcontroller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = {"", "/listblog"}, method = RequestMethod.GET)
    public String getAllBlog(ModelMap modelMap, HttpServletRequest request) {
        //data send to html:ModelMap
        String page = request.getParameter("page");
        System.out.println(page);
        if (page == null) {
            page = "1";
            int pageSize = 2;
            int pageIndex = Integer.parseInt(page);
            ArrayList<Blog> listBlogByIndex = blogService.getBlogByIndexPage(pageIndex);
            long totalRecord = blogService.countBlog();
            long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1;

            modelMap.addAttribute("totalPage", totalPage);
            modelMap.addAttribute("pageIndex", pageIndex);
            modelMap.addAttribute("listBlog", listBlogByIndex);
        } else {
            page = request.getParameter("page");
            int pageSize = 2;
            int pageIndex = Integer.parseInt(page);
            ArrayList<Blog> listBlog = blogService.getBlogByIndexPage(pageIndex);
            long totalRecord = blogService.countBlog();
            long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1;

            modelMap.addAttribute("totalPage", totalPage);
            modelMap.addAttribute("pageIndex", pageIndex);
            modelMap.addAttribute("listBlog", listBlog);
        }
        return "blog";
    }

    @RequestMapping(value = "/blogdetail", method = RequestMethod.GET)
    public String getBlogDetail(ModelMap modelMap, HttpServletRequest request) {
        //data send to html:ModelMap
        int blogid = Integer.parseInt(request.getParameter("blogid"));
        Blog blog = blogService.getDetailBlog(blogid);
        modelMap.addAttribute("blogdetail", blog);
        return "blog_details";
    }
}
