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

    @Query("SELECT qh.quiz_history_name, DATE(qh.history_time), qh.num_of_question,COUNT(a2.is_correct)\n" +
            "as correct\n" +
            "FROM quiz_history as qh INNER JOIN quiz_history_question q on qh.quiz_history_id = q.quiz_history_id\n" +
            "INNER JOIN answer a2 on a2.answer_id = q.user_answer\n" +
            "WHERE qh.history_account_id = ?1\n" +
            "GROUP BY qh.quiz_history_id")
    List<QuizHistory> listHistoryQuiz(int historyAccountId);
}
