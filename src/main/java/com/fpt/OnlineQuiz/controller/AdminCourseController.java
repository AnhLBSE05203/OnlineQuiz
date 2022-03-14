package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.CourseAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.service.SubjectService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(Constants.LINK_ADMIN_COURSE_CONTROLLER)
public class AdminCourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private SubjectService subjectService;

    @PostMapping(Constants.LINK_ADMIN_COURSE_PROCESS_EDIT)
    public String editCourse(@ModelAttribute(Constants.ATTRIBUTE_COURSE_EDIT_DTO) CourseAdminDTO courseAdminDTO, HttpServletRequest request) {
        Course course = courseService.getById(courseAdminDTO.getId());
        course.setFromCourseAdminDTO(courseAdminDTO);
        //get original subject id
        int originalSubjectId = Integer.parseInt(request.getParameter("originalSubjectId"));
        if (originalSubjectId != courseAdminDTO.getSubjectId()) {
            //get original subject
            Subject subjectOld = subjectService.getSubjectById(originalSubjectId);
            subjectOld.getCourses().remove(course);
            //get new subject
            Subject subjectNew = subjectService.getSubjectById(courseAdminDTO.getSubjectId());
            course.setSubject(subjectNew);
            subjectNew.getCourses().add(course);
            subjectService.updateSubject(subjectOld);
            subjectService.updateSubject(subjectNew);
        }
        courseService.updateCourse(course);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @PostMapping(Constants.LINK_ADMIN_COURSE_ADD)
    public String addCourse(@ModelAttribute(Constants.ATTRIBUTE_SUBJECT_ADD_DTO) CourseAdminDTO courseAdminDTO) {
        Course course = new Course();
        course.setFromCourseAdminDTO(courseAdminDTO);
        //add course to subject
        Subject subject = subjectService.getSubjectById(courseAdminDTO.getSubjectId());
        course.setSubject(subject);
        subject.getCourses().add(course);
        subjectService.updateSubject(subject);
        courseService.updateCourse(course);

        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @PostMapping(value = Constants.LINK_ADMIN_COURSE_GET_COURSES_BY_SUBJECT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<CourseAdminDTO> getCoursePageBySubject(@RequestBody PagingRequest pagingRequest) {
        int subjectId = Integer.parseInt(pagingRequest.getPrefilter());
        Page<CourseAdminDTO> courseAdminDTOs = courseService.getCourseAdminDTOByPagingRequest(subjectId, pagingRequest);
        return courseAdminDTOs;
    }

    @GetMapping(value = Constants.LINK_ADMIN_COURSE_DELETE)
    public String deleteCourse(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        course.setStatus(Constants.STATUS_DELETED);
        courseService.updateCourse(course);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @GetMapping(value = Constants.LINK_ADMIN_COURSE_RECOVER)
    public String recoverCourse(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        course.setStatus(Constants.STATUS_COURSE_ACTIVE);
        courseService.updateCourse(course);
        return Constants.LINK_REDIRECT + Constants.LINK_ADMIN_SUBJECT_CONTROLLER;
    }

    @GetMapping(value = Constants.LINK_ADMIN_COURSE_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CourseAdminDTO getCourseDetails(@PathVariable Integer id) {
        CourseAdminDTO courseDTO = courseService.getCourseAdminDTOById(id);
        return courseDTO;
    }

    @GetMapping(value = Constants.LINK_ADMIN_COURSE_GET_DUPLICATE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean isDuplicated(HttpServletRequest request) {
        String name = request.getParameter("name");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        boolean isDuplicated = courseService.isDuplicated(name, subjectId);
        System.out.println(isDuplicated);
        return isDuplicated;
    }
}
