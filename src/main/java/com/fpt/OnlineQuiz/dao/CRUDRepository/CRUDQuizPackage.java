package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.LessonType;
import com.fpt.OnlineQuiz.model.Quiz;
import com.fpt.OnlineQuiz.model.QuizPackage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRUDQuizPackage extends CrudRepository<QuizPackage,Integer> {
    @Query("select q from QuizPackage q where q.account.id = ?1")
    List<QuizPackage> findByAccountId(int id);

    @Query("select q from Quiz q where q.quizPackage.id = ?1")
    List<Quiz> listQuizByPackage(int id);
}
