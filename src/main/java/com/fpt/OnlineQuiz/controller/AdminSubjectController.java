package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Image;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.ImageService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/subject")
public class AdminSubjectController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ImageService imageService;

    @GetMapping(value = {"", "/"})
    public String subjectPage(Model model) {
        model.addAttribute("subjectEditDTO", new SubjectAdminDTO());
        model.addAttribute("subjectAddDTO", new SubjectAdminDTO());
        model.addAttribute("statusMap", Constants.subjectStatusConversion);
        return "admin_subject_page";
    }

    @PostMapping("/edit")
    public String editSubject(@ModelAttribute("subjectEditDTO") SubjectAdminDTO subjectAdminDTO) {
        Subject subject = subjectService.getSubjectById(subjectAdminDTO.getId());
        subject.setName(subjectAdminDTO.getName());
        //set img - to do: image upload
        subject.setStatus(subjectAdminDTO.getStatus());

        subjectService.updateSubject(subject);
        return "redirect:/admin/subject";
    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute("subjectAddDTO") SubjectAdminDTO subjectAdminDTO) {
        //to do - add form to page
        Subject subject = new Subject();
        subject.setName(subjectAdminDTO.getName());
        //set img - to do: image upload
        //set default img - temporary
        Image defaultImg = imageService.getById(Constants.DEFAULT_SUBJECT_IMAGE_ID);
        if (defaultImg == null) {
            defaultImg = new Image();
            defaultImg.setDefaultImg();
            imageService.addImage(defaultImg);
        }
        subject.setImage(defaultImg);
        //
        subject.setStatus(subjectAdminDTO.getStatus());
        subjectService.addSubject(subject);
        return "redirect:/admin/subject";
    }

    @PostMapping(value = "/getSubjectsByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<SubjectAdminDTO> getSubjectsByPage(@RequestBody PagingRequest pagingRequest) {
        Page<SubjectAdminDTO> listSubjectDTO = subjectService.getByPagingRequest(pagingRequest);

        return listSubjectDTO;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SubjectAdminDTO getSubjectDetails(@PathVariable Integer id) {
        SubjectAdminDTO subjectDTO = subjectService.getSubjectAdminDTOById(id);
        return subjectDTO;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteSubject(@PathVariable Integer id) {
        Subject subject = subjectService.getSubjectById(id);
        subject.setStatus(Constants.STATUS_SUBJECT_DELETED);
        subjectService.updateSubject(subject);
        return "redirect:/admin/subject";
    }

    @GetMapping(value = "/recover/{id}")
    public String recoverSubject(@PathVariable Integer id) {
        Subject subject = subjectService.getSubjectById(id);
        subject.setStatus(Constants.STATUS_SUBJECT_ACTIVE);
        subjectService.updateSubject(subject);
        return "redirect:/admin/subject";
    }
}
