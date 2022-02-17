package com.fpt.OnlineQuiz.dao.DaiNT_CRUD_Interface;

import com.fpt.OnlineQuiz.model.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//DaiNT -r
//Spring Data JPA CRUD Repository,thêm,sửa,xóa lesson
@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {

    @Query("select l from Lesson l where l.subject.id = ?1")
    List<Lesson> findBySubId(int subId);

}
