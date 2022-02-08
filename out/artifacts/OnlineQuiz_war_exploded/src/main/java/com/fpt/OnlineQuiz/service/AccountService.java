package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    void addAccount(Account account);

    Account findAccountByEmail(String email);
}
