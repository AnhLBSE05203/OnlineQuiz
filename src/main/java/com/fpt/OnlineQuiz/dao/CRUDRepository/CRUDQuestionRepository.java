package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRUDQuestionRepository extends CrudRepository<Question, Integer> {

    @Query("select q from Question q where q.lesson.id = ?1")
    List<Question> findBySubId(int subId);
}