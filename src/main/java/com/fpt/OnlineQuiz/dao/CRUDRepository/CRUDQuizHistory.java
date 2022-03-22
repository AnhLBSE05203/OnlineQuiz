package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.QuizHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRUDQuizHistory extends CrudRepository<QuizHistory, Integer> {

    @Query("SELECT qh FROM QuizHistory qh JOIN QuizHistoryAccountAdd qhaa " +
            "on qh.id = qhaa.quizHistory.id\n" +
            "WHERE qhaa.account.id = ?1")
    List<QuizHistory> listQuizByAccountAdd(int accountId);

    @Query("SELECT qh\n" +
            "FROM QuizHistory as qh INNER JOIN QuizHistoryQuestion q on qh.id = q.quizHistory.id\n" +
            "INNER JOIN Answer a2 on a2.id = q.userAnswer.id\n" +
            "WHERE qh.accountHistory.id = ?1\n" +
            "GROUP BY qh.id")
    List<QuizHistory> listHistoryQuiz(int historyAccountId);

    @Query("SELECT qh FROM QuizHistory qh WHERE qh.id = ?1")
    QuizHistory findForAdd(int id);

    @Query("SELECT qh FROM QuizHistory qh WHERE qh.account.id IS not NULL AND qh.name LIKE %:packName%")
    List<QuizHistory> allIfAccountNotNull(@Param("packName") String name);

    @Query("SELECT qh FROM QuizHistory qh INNER JOIN QuizHistoryAccountAdd qhaa " +
            "on qh.id = qhaa.quizHistory.id " +
            "WHERE qh.id = ?1 AND qhaa.account.id = ?2")
    QuizHistory checkExist(int historyId,int accountId);
}
