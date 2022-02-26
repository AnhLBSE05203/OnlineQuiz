package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.LessonType;
import org.springframework.stereotype.Service;

@Service
public interface LessonTypeService {
    LessonType getByName(String name);

}
