package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.service.QuestionService;
import com.fpt.OnlineQuiz.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    QuizHistoryService quizHistoryService;

    @Autowired
    QuestionService questionService;

    @GetMapping(value = "")
    public String practicelListPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
       /*Get all QuizHistory(1 quizHistory contain multiple questions) existed in user account.
       Same like quizlet,user add học phần (quizHistory) in their practices list,it wll display
       in here (practices list)
       */
        List<QuizHistory> quizHistories = quizHistoryService.getQuizByAccountAdd(account.getId());
//        Iterable<QuizPackageHistory> quizList = quizPackageService.getAllQuiz();
        model.addAttribute("quizHistory", quizHistories);
//        model.addAttribute("quizList", quizList);
//        model.addAttribute("account", account);

        return "practices_list_page";
    }
    @GetMapping(value = "/detail")
    public String practiceDetailPage(Model model, @Param("id") int id) {
        List<Question> questions = questionService.getQuestionQHid(id);
        model.addAttribute("questions", questions);
        return "practices_detail_page";
    }

}
