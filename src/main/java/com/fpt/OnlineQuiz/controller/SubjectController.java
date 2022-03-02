package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dao.SubjectRepository;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(path = "/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping(path = {"", "/listSubject"})
    public String showMySubjectPage(ModelMap modelMap, HttpServletRequest request) {
        String page = request.getParameter("page");
        int pageSize = Constants.USER_SUBJECT_PAGE_SIZE;
        if (page == null) {
            page = "1";
            int pageIndex = Integer.parseInt(page);
            List<Subject> listSubject = subjectService.findAllSubjectsByPaging(pageIndex, pageSize);
            long totalRecord = subjectRepository.countSubject();
            long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1;

            modelMap.addAttribute("totalPage", totalPage);
            modelMap.addAttribute("pageIndex", pageIndex);
            modelMap.addAttribute("listSubject", listSubject);
        } else {
            int pageIndex = Integer.parseInt(page);
            List<Subject> listSubject = subjectService.findAllSubjectsByPaging(pageIndex, pageSize);
            long totalRecord = subjectRepository.countSubject();
            long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize) + 1;

            modelMap.addAttribute("totalPage", totalPage);
            modelMap.addAttribute("pageIndex", pageIndex);
            modelMap.addAttribute("listSubject", listSubject);
        }
        return "listSubjectUser";
    }

    @GetMapping(path = "/loadMoreSubject")
    public @ResponseBody
    void loadMore(
            @RequestParam("amount") String amount, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        //todo - fix hardcode accountId
        //Account account = ...
        int accountId = 3; //...
        int amountInt = Integer.parseInt(amount);
        System.out.println(amountInt);
        List<Subject> subjects = subjectService.getNext3Subject(accountId, amountInt);
        if (subjects.size() != 0) {
            System.out.println("list size: " + subjects.size());
            for (Subject s : subjects) {
                out.println("<div class=\"col-lg-4 subject\">\n" +
                        "                    <div class=\"properties properties2 mb-30\">\n" +
                        "                        <div class=\"properties__card\">\n" +
                        "                            <div class=\"properties__img overlay1\">\n" +
                        "                                <a href=\"#\"><img src=\"/img/gallery/featured1.png\" alt=\"\"></a>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"properties__caption\">\n" +
                        "                                <p> " + s.getName() + "</p>\n" +
                        "                                <a href=\"#\" class=\"border-btn border-btn2\">Go to course</a>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>");
            }
        }
    }
}