package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.LessonType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRUDLessonTypeRepository extends CrudRepository<LessonType,String> {


    @Query("select l from LessonType l where l.name = ?1")
    LessonType findByName(String name);

    @Query("select l from LessonType l where l.id = ?1")
    LessonType findByiId(int id);
}
