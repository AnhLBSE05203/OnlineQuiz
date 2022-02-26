package com.fpt.OnlineQuiz.service.implement;


import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDLessonTypeRepository;
import com.fpt.OnlineQuiz.model.LessonType;
import com.fpt.OnlineQuiz.service.LessonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonTypeServiceImpl implements LessonTypeService {

    @Autowired
    CRUDLessonTypeRepository crudLessonTypeRepository;

    @Override
    public LessonType getByName(String name) {
        return crudLessonTypeRepository.findByName(name);
    }
}
