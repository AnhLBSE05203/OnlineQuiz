package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject>  getAllMySubject(int account_id);

    List<Subject> getFeaturedSubjects(int number);
}
