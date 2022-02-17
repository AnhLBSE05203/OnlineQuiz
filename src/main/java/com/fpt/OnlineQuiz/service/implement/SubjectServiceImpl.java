package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CourseRepository;
import com.fpt.OnlineQuiz.dao.SubjectRepository;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllMySubject(int account_id) {
        List<Course> list_course = courseRepository.getTop3Courses(account_id);
        List<Subject> list_subject = new ArrayList<>();
        for (int i = 0; i < list_course.size(); i++){
             list_subject.add(list_course.get(i).getSubject());
        }
        return list_subject;
    }

    @Override
    public List<Subject> getNext3Subject(int account_id, int amount) {
        List<Course> list_course = courseRepository.getNext3Courses(account_id, amount);
        List<Subject> list_subject = new ArrayList<>();
        for (int i = 0; i < list_course.size(); i++){
            list_subject.add(list_course.get(i).getSubject());
        }
        return list_subject;
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
        for(Subject subject : listSubjects) {
            SubjectAdminDTO subjectAdminDTO = subject.toSubjectAdminDTO();
            listSubjectDTO.add(subjectAdminDTO);
        }
        return listSubjectDTO;
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
    public void update(Subject subject) {
        subjectRepository.update(subject);
    }
}
