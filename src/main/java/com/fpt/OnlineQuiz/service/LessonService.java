package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LessonService {
    public List<Lesson> getAllLesson(int subjectId);
}
