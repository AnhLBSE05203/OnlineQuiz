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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(Constants.LINK_ADMIN_SUBJECT_CONTROLLER)
public class AdminSubjectController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ImageService imageService;

    @GetMapping(value = {Constants.STRING_EMPTY, Constants.LINK_ADMIN_SUBJECT_LIST})
    public String subjectPage(Model model) {
        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_EDIT_DTO, new SubjectAdminDTO());
        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_ADD_DTO, new SubjectAdminDTO());
        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_STATUS_MAP, Constants.subjectStatusConversion);
        return Constants.PAGE_ADMIN_SUBJECT_PAGE;
    }

    @PostMapping(Constants.LINK_ADMIN_SUBJECT_PROCESS_EDIT)
    public String editSubject(@ModelAttribute(Constants.ATTRIBUTE_SUBJECT_EDIT_DTO) SubjectAdminDTO subjectAdminDTO) {
        Subject subject = subjectService.getSubjectById(subjectAdminDTO.getId());
        subject.setFromSubjectAdminDTO(subjectAdminDTO);
        //todo: add image upload

        subjectService.updateSubject(subject);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @PostMapping(Constants.LINK_ADMIN_SUBJECT_ADD)
    public String addSubject(@ModelAttribute(Constants.ATTRIBUTE_SUBJECT_ADD_DTO) SubjectAdminDTO subjectAdminDTO) {
        //todo - add image upload
        Subject subject = new Subject();
        subject.setFromSubjectAdminDTO(subjectAdminDTO);

        //set default img - temporary
        Image defaultImg = imageService.getById(Constants.DEFAULT_SUBJECT_IMAGE_ID);
        if (defaultImg == null) {
            defaultImg = new Image();
            defaultImg.setDefaultImg();
            imageService.addImage(defaultImg);
        }
        subject.setImage(defaultImg);
        //

        subjectService.addSubject(subject);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @PostMapping(value = Constants.LINK_ADMIN_SUBJECT_GET_BY_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<SubjectAdminDTO> getSubjectsByPage(@RequestBody PagingRequest pagingRequest) {
        Page<SubjectAdminDTO> listSubjectDTO = subjectService.getByPagingRequest(pagingRequest);
        return listSubjectDTO;
    }

    @GetMapping(value = Constants.LINK_ADMIN_SUBJECT_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SubjectAdminDTO getSubjectDetails(@PathVariable Integer id) {
        SubjectAdminDTO subjectDTO = subjectService.getSubjectAdminDTOById(id);
        return subjectDTO;
    }

    @GetMapping(value = Constants.LINK_ADMIN_SUBJECT_GET_BY_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SubjectAdminDTO getByName(HttpServletRequest request) {
        String name = request.getParameter("name");
        Subject subject = subjectService.getSubjectByNameLower(name);
        SubjectAdminDTO subjectAdminDTO = subject.toSubjectAdminDTO();
        return subjectAdminDTO;
    }

    @GetMapping(value = Constants.LINK_ADMIN_SUBJECT_DELETE)
    public String deleteSubject(@PathVariable Integer id) {
        Subject subject = subjectService.getSubjectById(id);
        subject.setStatus(Constants.STATUS_DELETED);
        subjectService.updateSubject(subject);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @GetMapping(value = Constants.LINK_ADMIN_SUBJECT_RECOVER)
    public String recoverSubject(@PathVariable Integer id) {
        Subject subject = subjectService.getSubjectById(id);
        subject.setStatus(Constants.STATUS_SUBJECT_ACTIVE);
        subjectService.updateSubject(subject);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }
}
