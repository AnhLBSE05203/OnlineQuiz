package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "commentcontroller")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    String commentBlog(ModelMap modelMap, HttpServletRequest request) {
        String content = request.getParameter("");
        int blogId = Integer.parseInt(request.getParameter(""));
        commentService.commentBlog(blogId, content);
        return null;
    }
}
