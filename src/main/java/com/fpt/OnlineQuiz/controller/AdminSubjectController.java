package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.CourseAdminDTO;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Image;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.service.ImageService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(Constants.LINK_ADMIN_SUBJECT_CONTROLLER)
public class AdminSubjectController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CourseService courseService;

    @GetMapping(value = {Constants.STRING_EMPTY, Constants.LINK_ADMIN_SUBJECT_LIST})
    public String subjectPage(Model model) {
        //subject + course modals form dtos
        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_EDIT_DTO, new SubjectAdminDTO());
        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_ADD_DTO, new SubjectAdminDTO());
        model.addAttribute(Constants.ATTRIBUTE_COURSE_EDIT_DTO, new CourseAdminDTO());
        model.addAttribute(Constants.ATTRIBUTE_COURSE_ADD_DTO, new CourseAdminDTO());

        //subject status map for subject add/edit
        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_STATUS_MAP, Constants.subjectStatusConversion);

        //course status map for course add/edit
        model.addAttribute(Constants.ATTRIBUTE_COURSE_STATUS_MAP, Constants.courseStatusConversion);

        List<SubjectAdminDTO> subjectList = subjectService.getAllSubjectAdminDTO();
        //subject list for course add/edit
        model.addAttribute(Constants.ATTRIBUTE_SUBJECT_LIST, subjectList);
        return Constants.PAGE_ADMIN_SUBJECT_PAGE;
    }

    @PostMapping(Constants.LINK_ADMIN_SUBJECT_PROCESS_EDIT)
    public String editSubject(@ModelAttribute(Constants.ATTRIBUTE_SUBJECT_EDIT_DTO) SubjectAdminDTO subjectAdminDTO,
                              @RequestParam(Constants.REQUEST_PARAM_FILE) MultipartFile file, HttpServletRequest request) {
        Subject subject = subjectService.getSubjectById(subjectAdminDTO.getId());
        subject.setFromSubjectAdminDTO(subjectAdminDTO);
        if (file != null & !file.isEmpty()) {
            Image image = new Image();
            Date now = new Date();
            image.setStatus(Constants.STATUS_DEFAULT);
            image.setCreatedTime(now);
            image.setUpdatedTime(now);
            image.setCreatedUserId(1);
            imageService.addImage(image);
            subject.setImage(image);
            subjectService.updateSubject(subject);
            request.setAttribute(Constants.REQUEST_ATTRIBUTE_IMG_ID, image.getId());
            request.setAttribute(Constants.REQUEST_ATTRIBUTE_RETURN_LINK, Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER);
            return Constants.LINK_FORWARD + Constants.LINK_IMAGE_CONTROLLER + Constants.LINK_IMAGE_CONTROLLER_UPLOAD_IMAGE;
        }
        subjectService.updateSubject(subject);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @PostMapping(Constants.LINK_ADMIN_SUBJECT_ADD)
    public String addSubject(@ModelAttribute(Constants.ATTRIBUTE_SUBJECT_ADD_DTO) SubjectAdminDTO subjectAdminDTO,
                             @RequestParam(Constants.REQUEST_PARAM_FILE) MultipartFile file, HttpServletRequest request) {
        //todo - add image upload
        Subject subject = new Subject();
        subject.setFromSubjectAdminDTO(subjectAdminDTO);
        Image image;
        if (file == null || file.isEmpty()) {
            //set default img - temporary
            image = imageService.getById(Constants.DEFAULT_SUBJECT_IMAGE_ID);
            if (image == null) {
                image = new Image();
                image.setDefaultImg();
                imageService.addImage(image);
            }
            subject.setImage(image);
            subjectService.addSubject(subject);
            return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
        } else {
            image = new Image();
            Date now = new Date();
            image.setStatus(Constants.STATUS_DEFAULT);
            image.setCreatedTime(now);
            image.setUpdatedTime(now);
            image.setCreatedUserId(1);
            imageService.addImage(image);
        }
        subject.setImage(image);
        subjectService.addSubject(subject);
        request.setAttribute(Constants.REQUEST_ATTRIBUTE_IMG_ID, image.getId());
        request.setAttribute(Constants.REQUEST_ATTRIBUTE_RETURN_LINK, Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER);
        return Constants.LINK_FORWARD + Constants.LINK_IMAGE_CONTROLLER + Constants.LINK_IMAGE_CONTROLLER_UPLOAD_IMAGE;
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

    @GetMapping(value = Constants.LINK_ADMIN_SUBJECT_GET_DUPLICATE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean isDuplicated(HttpServletRequest request) {
        boolean isDuplicated = false;
        String name = request.getParameter(Constants.REQUEST_PARAM_SUBJECT_NAME);
        Subject subject = subjectService.getSubjectByNameLower(name);
        if (subject != null) {
            isDuplicated = true;
        }
        return isDuplicated;
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
