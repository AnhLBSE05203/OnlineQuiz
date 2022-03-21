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

    @Query("select q from Question q where q.quizHistory.id = ?1")
    List<Question> findByQuizHistoryId(int id);

    @Query("select q From Question q INNER JOIN Answer a on q.id = a.question.id " +
            "INNER JOIN QuizHistory qh on qh.id = ?1")
    List<Question> historyQuestion(int quizHisId);
}
