package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.LessonType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LessonTypeService {
    LessonType getByName(String name);
    List<Lesson> getAllType(int id);
    List<LessonType> getAllTypes();
}
