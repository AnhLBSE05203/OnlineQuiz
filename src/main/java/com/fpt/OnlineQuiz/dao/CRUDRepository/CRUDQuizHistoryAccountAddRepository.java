package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.QuizHistoryAccountAdd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRUDQuizHistoryAccountAddRepository extends CrudRepository<QuizHistoryAccountAdd,Integer> {
}
