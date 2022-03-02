package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.utils.Constants;
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

        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_EDIT_DTO, new SubjectAdminDTO());

        return "practices_list_page";
    }

}
