package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDSubjectRepository;
import com.fpt.OnlineQuiz.dto.LessonAdminDTO;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Image;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.LessonType;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.ImageService;
import com.fpt.OnlineQuiz.service.LessonService;
import com.fpt.OnlineQuiz.service.LessonTypeService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/lesson")
public class AdminLessonController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CRUDSubjectRepository crudSubjectRepository;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private LessonTypeService lessonTypeService;

    @GetMapping(value = {"", "/"})
    public String lessonPage(Model model) {
        List<Subject> subjectList = subjectService.findAllSubjects();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("lessonEditDTO", new LessonAdminDTO());
        model.addAttribute("lessonAddDTO", new LessonAdminDTO());
        model.addAttribute("statusMap", Constants.subjectStatusConversion);
        return "admin_lesson_page";
    }

//    @PostMapping("/edit")
//    public String editSubject(@ModelAttribute("subjectEditDTO") SubjectAdminDTO subjectAdminDTO) {
//        Subject subject = subjectService.getSubjectById(subjectAdminDTO.getId());
//        subject.setName(subjectAdminDTO.getName());
//        //set img - to do: image upload
//        subject.setStatus(subjectAdminDTO.getStatus());
//
//        subjectService.updateSubject(subject);
//        return "redirect:/admin/lesson";
//    }

    @PostMapping("/add")
    public String addSubject(@ModelAttribute("lessonAddDTO") LessonAdminDTO lessonAdminDTO) {
        //to do - add form to page
        Lesson lesson = new Lesson();
        lesson.setName(lessonAdminDTO.getName());

        LessonType lessonType = lessonTypeService.getByName(lessonAdminDTO.getLessonType());
        lesson.setLessonType(lessonType);
        Subject subject = subjectService.findSubByName(lessonAdminDTO.getSubjects());
        lesson.setSubject(subject);
        lesson.setContent(lessonAdminDTO.getContent());
        lesson.setStatus("Not Start");
        lesson.setTime(lessonAdminDTO.getTime());
        //set img - to do: image upload
        //set default img - temporary
//        Image defaultImg = imageService.getById(Constants.DEFAULT_SUBJECT_IMAGE_ID);
//        if (defaultImg == null) {
//            defaultImg = new Image();
//            defaultImg.setDefaultImg();
//            imageService.addImage(defaultImg);
//        }
//        lesson.setImage(defaultImg);
//        //
//        lesson.setStatus(Constants.STATUS_SUBJECT_ACTIVE);
        lessonService.addLesson(lesson);
        return "redirect:/admin/lesson";
    }

    @PostMapping(value = "/getLessonByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<LessonAdminDTO> getLessonByPage(@RequestBody PagingRequest pagingRequest) {
        Page<LessonAdminDTO> lessonAdminDTOPage = lessonService.getByPagingRequest(pagingRequest);
        return lessonAdminDTOPage;
    }

//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public SubjectAdminDTO getSubjectDetails(@PathVariable Integer id) {
//        SubjectAdminDTO subjectDTO = subjectService.getSubjectAdminDTOById(id);
//        return subjectDTO;
//    }

//    @GetMapping(value = "/delete/{id}")
//    public String deleteSubject(@PathVariable Integer id) {
//        Subject subject = subjectService.getSubjectById(id);
//        subject.setStatus(Constants.STATUS_SUBJECT_DELETED);
//        subjectService.updateSubject(subject);
//        return "redirect:/admin/lesson";
//    }

//    @GetMapping(value = "/recover/{id}")
//    public String recoverSubject(@PathVariable Integer id) {
//        Subject subject = subjectService.getSubjectById(id);
//        subject.setStatus(Constants.STATUS_SUBJECT_ACTIVE);
//        subjectService.updateSubject(subject);
//        return "redirect:/admin/lesson";
//    }
}
