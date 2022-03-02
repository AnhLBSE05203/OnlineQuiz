package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.QuizPackage;
import com.fpt.OnlineQuiz.service.QuizPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/practices")
public class PracticeController {

    @Autowired
    QuizPackageService quizPackageService;

    @GetMapping(value = "")
    public String subjectPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        //Get all quizPackage
        List<QuizPackage> quizPackageList = quizPackageService.finByAccountId(account.getId());

        Iterable<QuizPackage> quizList = quizPackageService.getAllQuiz();
        model.addAttribute("quizPackage", quizPackageList);
        model.addAttribute("quizList", quizList);

        return "practices_list_page";
    }

}
