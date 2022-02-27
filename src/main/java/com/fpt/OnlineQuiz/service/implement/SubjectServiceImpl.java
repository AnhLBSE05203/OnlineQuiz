package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDSubjectRepository;
import com.fpt.OnlineQuiz.dao.CourseRepository;
import com.fpt.OnlineQuiz.dao.SubjectRepository;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CRUDSubjectRepository CRUDSubjectRepository;

    @Override
    public List<Subject> findAllSubject() {
        return subjectRepository.findAllSubjects();
    }

    @Override
    public List<Subject> getAllSubject(int accountId) {
        List<Course> list_course = courseRepository.getTop3Courses(accountId);
        List<Subject> list_subject = new ArrayList<>();
        for (int i = 0; i < list_course.size(); i++) {
            list_subject.add(list_course.get(i).getSubject());
        }
        return list_subject;
    }

    @Override
    public List<Subject> getNext3Subject(int accountId, int start) {
        List<Course> listCourses = courseRepository.getNext3Courses(accountId, start);
        List<Subject> listSubjects = new ArrayList<>();
        for (int i = 0; i < listCourses.size(); i++) {
            listSubjects.add(listCourses.get(i).getSubject());
        }
        return listSubjects;
    }

    @Override
    public List<Subject> getFeaturedSubjects(int number) {
        return subjectRepository.getTopNumberOfSubjects(number);
    }

    @Override
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAllSubjects();
    }

    @Override
    public List<SubjectAdminDTO> getAllSubjectAdminDTO() {
        List<Subject> listSubjects = subjectRepository.findAllSubjects();
        List<SubjectAdminDTO> listSubjectDTO = new ArrayList<>();
        for (Subject subject : listSubjects) {
            SubjectAdminDTO subjectAdminDTO = subject.toSubjectAdminDTO();
            listSubjectDTO.add(subjectAdminDTO);
        }
        return listSubjectDTO;
    }

    @Override
    public Page<SubjectAdminDTO> getByPagingRequest(PagingRequest pagingRequest) {
        List<Subject> subjects = subjectRepository.getByPagingRequest(pagingRequest);
        long count = subjectRepository.getSubjectCountByPagingRequest(pagingRequest);
        List<SubjectAdminDTO> subjectAdminDTOs = new ArrayList<>();
        // convert Subject to SubjectAdminDTO
        for (Subject subject : subjects) {
            SubjectAdminDTO subjectAdminDTO = subject.toSubjectAdminDTO();
            subjectAdminDTOs.add(subjectAdminDTO);
        }
        // convert List to Page
        Page<SubjectAdminDTO> page = new Page<>(subjectAdminDTOs);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    @Override
    public SubjectAdminDTO getSubjectAdminDTOById(int id) {
        Subject subject = subjectRepository.getSubjectById(id);
        SubjectAdminDTO subjectAdminDTO = subject.toSubjectAdminDTO();
        return subjectAdminDTO;
    }

    @Override
    public Subject getSubjectById(int id) {
        return subjectRepository.getSubjectById(id);
    }

    @Override
    public void updateSubject(Subject subject) {
        subjectRepository.updateSubject(subject);
    }

    @Override
    public void addSubject(Subject subject) {
        subjectRepository.addSubject(subject);
    }

    @Override
    public List<Subject> findAllSubjectsByPaging(int pageIndex, int pageSize) {
        return subjectRepository.findAllSubjectsByPaging(pageIndex, pageSize);
    }

    //get specific subject by subName
    @Override
    public Subject findSubByName(String name) {
        return CRUDSubjectRepository.findByName(name);
    }

    //get specific subject by subjectId
    @Override
    public Optional<Subject> getSubject(int id) {
        return CRUDSubjectRepository.findById(id);
    }
}
