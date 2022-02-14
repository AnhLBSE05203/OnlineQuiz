package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dao.BlogRepository;
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
@RequestMapping(path = "blogcontroller")
//http:localhost:8080/blogcontroller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getAllBlog(ModelMap modelMap){
        //data send to html:ModelMap
        ArrayList<Blog> listBlog = blogService.getAllBlog();
        //modelMap.addAttribute("listBlog",listBlog);
        String page = "1";
        int pageSize = 2;
        int pageIndex = Integer.parseInt(page);
        ArrayList<Blog> listBlogByIndex = blogService.getBlogByIndexPage(pageIndex);
        long totalRecord = blogService.countBlog();
        long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1;

        modelMap.addAttribute("totalPage",totalPage);
        modelMap.addAttribute("pageIndex",pageIndex);
        modelMap.addAttribute("listBlog",listBlogByIndex);
        return "blog";
    }
    @RequestMapping(value = "",method = RequestMethod.POST)
    public String getPageIndexAndPaging(ModelMap modelMap, HttpServletRequest request){
        //data send to html:ModelMap
        String page = request.getParameter("page");
        int pageSize = 2;
        if(page == null || page.length()== 0){
            page = "1";
        }
        int pageIndex = Integer.parseInt(page);
        ArrayList<Blog> listBlog = blogService.getBlogByIndexPage(pageIndex);
        long totalRecord = blogService.countBlog();
        long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1;

        modelMap.addAttribute("totalPage",totalPage);
        modelMap.addAttribute("pageIndex",pageIndex);
        modelMap.addAttribute("listBlog",listBlog);
        return "blog";
    }
}
