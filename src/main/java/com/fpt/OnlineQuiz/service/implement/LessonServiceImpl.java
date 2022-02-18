package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.DaiNT_CRUD_Interface.LessonRepository;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAllLesson(int subjectId) {
        return (List<Lesson>) lessonRepository.findBySubId(subjectId);
    }

    @Override
    public Optional<Lesson> getLessonById(int id) {
        return lessonRepository.findById(id);
    }
}
