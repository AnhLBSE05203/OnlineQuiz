package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/practices")
public class PracticeController {



    @GetMapping(value = "")
    public String subjectPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
//        //Get all quizPackage
//        List<QuizPackageHistory> quizPackageHistoryList = quizPackageService.finByAccountId(account.getId());
//        Iterable<QuizPackageHistory> quizList = quizPackageService.getAllQuiz();
//        model.addAttribute("quizPackage", quizPackageHistoryList);
//        model.addAttribute("quizList", quizList);
//        model.addAttribute("account", account);

        return "practices_list_page";
    }

}
