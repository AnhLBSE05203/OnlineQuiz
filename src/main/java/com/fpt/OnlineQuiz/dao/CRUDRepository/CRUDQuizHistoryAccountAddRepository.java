package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.QuizHistoryAccountAdd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRUDQuizHistoryAccountAddRepository extends CrudRepository<QuizHistoryAccountAdd,Integer> {

    @Query("SELECT qha FROM QuizHistoryAccountAdd qha WHERE qha.account.id = ?1 AND qha.quizHistory.id = ?2")
    QuizHistoryAccountAdd findByAccountAndHisId(int accountId,int hisId);
}
