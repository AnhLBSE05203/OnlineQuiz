package com.fpt.OnlineQuiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "commentcontroller")
public class CommentController {
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    String test(ModelMap modelMap, HttpServletRequest request) {

        return null;
    }
}
