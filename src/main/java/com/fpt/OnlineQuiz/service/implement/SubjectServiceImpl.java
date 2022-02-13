package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CourseRepository;
import com.fpt.OnlineQuiz.dao.SubjectRepository;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private CourseRepository courseRepository;
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllMySubject(int account_id) {
        List<Course> list_course = courseRepository.getCourses(account_id);
        List<Subject> list_subject = new ArrayList<>();
        for (int i = 0; i < list_course.size(); i++){
             list_subject.add(list_course.get(i).getSubject());
        }
        return list_subject;
    }

    @Override
    public List<Subject> getFeaturedSubjects(int number) {
        return subjectRepository.getFeaturedSubjects(number);
    }
}
