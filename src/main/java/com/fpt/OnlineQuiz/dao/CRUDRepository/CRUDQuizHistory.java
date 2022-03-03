package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.QuizHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRUDQuizHistory extends CrudRepository<QuizHistory, Integer> {

    @Query("SELECT qh FROM QuizHistory qh JOIN QuizHistoryAccountAdd qhaa " +
            "on qh.id = qhaa.quizHistory.id\n" +
            "WHERE qhaa.account.id = ?1")
    List<QuizHistory> listQuizByAccountAdd(int accountId);
}
