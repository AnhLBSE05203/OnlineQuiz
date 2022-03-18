package com.fpt.OnlineQuiz.dao.CRUDRepository;

import com.fpt.OnlineQuiz.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRUDAccountRepository extends CrudRepository<Account, Integer> {

    @Query("SELECT a from Account a WHERE a.id = ?1")
    Account getAccountById(Integer id);

}
