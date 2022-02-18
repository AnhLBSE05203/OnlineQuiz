package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CRUDRepository.LessonRepository;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAllLesson(int subjectId) {
        return (List<Lesson>) lessonRepository.findBySubId(subjectId);
    }
}
