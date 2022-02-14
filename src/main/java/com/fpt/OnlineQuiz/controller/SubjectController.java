package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.service.implement.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping(path = "/mysubject")
    public String showMySubjectPage(ModelMap modelMap){
        List<Subject> list = subjectService.getAllMySubject(3);
        modelMap.addAttribute("my_subject_list", list);
        return "my_subject_page";
    }
    @GetMapping(path = "/loadmore")
    public @ResponseBody void loadMore(
           @RequestParam("amount")String amount, HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        int iamount = Integer.parseInt(amount);
        System.out.println(iamount);
        List<Subject> subjects = subjectService.getNext3Subject(3, iamount);
        for (Subject s : subjects){
            out.println("<div class=\"col-lg-4 product\">\n" +
                    "                    <div class=\"properties properties2 mb-30\">\n" +
                    "                        <div class=\"properties__card\">\n" +
                    "                            <div class=\"properties__img overlay1\">\n" +
                    "                                <a href=\"#\"><img th:href=\"@{/static/img/gallery/featured1.png}\" alt=\"\"></a>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"properties__caption\">\n" +
                    "                                <p th:text=\""+s.getName()+"\"></p>\n" +
                    "                                <h3><a href=\"#\">Fundamental of UX for Application design</a></h3>\n" +
                    "                                <p>The automated process all your website tasks. Discover tools and techniques to engage effectively with vulnerable children and young people.\n" +
                    "                                </p>\n" +
                    "                                <a href=\"#\" class=\"border-btn border-btn2\">Go to course</a>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>");
        }
    }
}