package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.LessonService;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/list")
    public String showlessionListPage(@Param(value = "subId") int subId,Model model, HttpServletRequest request){
        List<Lesson> list = lessonService.getAllLesson(subId);
        Optional<Subject> subject = subjectService.getSubject(subId);

        model.addAttribute("subject",subject);
        model.addAttribute("lessons",list);
        model.addAttribute("subId", subId);
        return "listLesson_page";
    }

    @GetMapping("/detail")
    public String showlessionDetailPage(@Param(value = "lesId") int lesId,@Param(value = "subId") int subId,Model model, HttpServletRequest request){
        Optional<Lesson> lesson = lessonService.getLessonById(lesId);
        Optional<Subject> subject = subjectService.getSubject(subId);

        model.addAttribute("subject",subject);
        model.addAttribute("lesson",lesson);
        return "lessonDetail_page";
    }
}
