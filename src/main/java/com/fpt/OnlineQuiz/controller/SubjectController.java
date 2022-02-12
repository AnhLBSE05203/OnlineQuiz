package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.implement.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(path = "/user/mysubject")
public class SubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String showMySubjectPage(ModelMap modelMap){
        List<Subject> list = subjectService.getAllMySubject(3);
        System.out.println(list.get(0));
        modelMap.addAttribute("my_subject_list", list);
        return "my_subject_page";
    }
}
