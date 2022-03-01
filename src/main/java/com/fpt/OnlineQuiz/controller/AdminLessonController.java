package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDSubjectRepository;
import com.fpt.OnlineQuiz.dto.LessonAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.LessonType;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.ImageService;
import com.fpt.OnlineQuiz.service.LessonService;
import com.fpt.OnlineQuiz.service.LessonTypeService;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

//
    @GetMapping(value = {"", "/"})
    public String lessonPage(Model model) {
        List<Subject> subjectList = subjectService.findAllSubjects();
        List<LessonType> lessonTypes = lessonTypeService.getAllTypes();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("lessonEditDTO", new LessonAdminDTO());
        model.addAttribute("lessonAddDTO", new LessonAdminDTO());
        model.addAttribute("lessonType", lessonTypes);
        return "admin_lesson_page";
    }

    @PostMapping("/edit")
    public String editSubject(@ModelAttribute("LessonEditDTO") LessonAdminDTO lessonAdminDTO) {
        Optional<Lesson> lesson = lessonService.getLessonById(lessonAdminDTO.getId());
        lesson.get().setName(lessonAdminDTO.getName());
        lesson.get().setLessonType(lessonTypeService.getByName(lessonAdminDTO.getLessonType()));
        lesson.get().setSubject(subjectService.findSubByName(lessonAdminDTO.getSubjects()));
        lesson.get().setContent(lessonAdminDTO.getContent());
        lesson.get().setTime(lessonAdminDTO.getTime());
        lessonService.updateLesson(lesson.get());
        return "redirect:/admin/lesson";
    }

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

        lessonService.addLesson(lesson);
        return "redirect:/admin/lesson";
    }

    @PostMapping(value = "/getLessonByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<LessonAdminDTO> getLessonByPage(@RequestBody PagingRequest pagingRequest) {
        Page<LessonAdminDTO> lessonAdminDTOPage = lessonService.getByPagingRequest(pagingRequest);
        return lessonAdminDTOPage;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LessonAdminDTO getSubjectDetails(@PathVariable Integer id) {
        LessonAdminDTO lessonAdminDTO = lessonService.getLessonAdminDTOById(id);
        return lessonAdminDTO;
    }

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
