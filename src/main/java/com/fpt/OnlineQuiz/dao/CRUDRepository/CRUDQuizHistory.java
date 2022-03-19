package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.QuizHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRUDQuizHistory extends CrudRepository<QuizHistory, Integer> {

    @Query("SELECT new QuizHistory (qh.name,qh.createdTime,qh.id,qh.quizCount,qh.accountName) FROM QuizHistory qh JOIN QuizHistoryAccountAdd qhaa " +
            "on qh.id = qhaa.quizHistory.id\n" +
            "WHERE qhaa.account.id = ?1")
    List<QuizHistory> listQuizByAccountAdd(int accountId);

    @Query("SELECT new QuizHistory(qh.name, qh.historyTime, qh.number,COUNT(a2.isCorrect))\n" +
            "FROM QuizHistory as qh INNER JOIN QuizHistoryQuestion q on qh.id = q.quizHistory.id\n" +
            "INNER JOIN Answer a2 on a2.id = q.userAnswer.id\n" +
            "WHERE qh.accountHistory.id = ?1\n" +
            "GROUP BY qh.id")
    List<QuizHistory> listHistoryQuiz(int historyAccountId);
}
