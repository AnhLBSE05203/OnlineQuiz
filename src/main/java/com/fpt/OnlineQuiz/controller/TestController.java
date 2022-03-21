package com.fpt.OnlineQuiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {
    @GetMapping(path = "")
    public void test(HttpServletRequest request){
        int lesson_id = Integer.parseInt(request.getParameter("lessonId"));
        int number = Integer.parseInt(request.getParameter("number"));
        System.out.println("lessonId = " + lesson_id + " number: " + number);
    }
}
