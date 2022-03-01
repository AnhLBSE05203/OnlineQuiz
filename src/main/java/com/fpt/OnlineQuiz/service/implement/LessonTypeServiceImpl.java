package com.fpt.OnlineQuiz.service.implement;


import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDLessonTypeRepository;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.LessonType;
import com.fpt.OnlineQuiz.service.LessonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonTypeServiceImpl implements LessonTypeService {

    @Autowired
    CRUDLessonTypeRepository crudLessonTypeRepository;

    @Override
    public LessonType getByName(String name) {
        return crudLessonTypeRepository.findByName(name);
    }

    @Override
    public List<Lesson> getAllType(int id) {
        return (List<Lesson>) crudLessonTypeRepository.findByiId(id);
    }

    @Override
    public List<LessonType> getAllTypes() {
        return (List<LessonType>)crudLessonTypeRepository.findAll();
    }
}
