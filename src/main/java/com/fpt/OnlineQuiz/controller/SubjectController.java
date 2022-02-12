package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.implement.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class SubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;

    @GetMapping(path = "/mysubject")
    public String showMySubjectPage(ModelMap modelMap){
        List<Subject> list = subjectService.getAllMySubject(3);
        modelMap.addAttribute("my_subject_list", list);
        return "my_subject_page";
    }
}
