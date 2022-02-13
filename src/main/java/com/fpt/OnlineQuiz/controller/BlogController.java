package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dao.BlogRepository;
import com.fpt.OnlineQuiz.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping(path = "blogcontroller")
//http:localhost:8080/blogcontroller
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getAllBlog(ModelMap modelMap){
        //data send to html:ModelMap
        ArrayList<Blog> listBlog = blogRepository.getAllBlog();
        System.out.println(listBlog.get(0));
        modelMap.addAttribute("listBlog",listBlog);
        return "blog";
    }
}
