package com.fpt.OnlineQuiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "blogcontroller")
//http:localhost:8080/blogcontroller
public class BlogController {
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getAllBlog(ModelMap modelMap){
        //data send to html:ModelMap
        System.out.println("oke");
        return "blog";
    }
}
