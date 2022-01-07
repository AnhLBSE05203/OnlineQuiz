package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    void addAccount(Account account);

    Account findAccountByEmail(String email);

    Account findByResetPasswordToken(String token);

    void updateResetPasswordToken(String token, String email);

    void updatePassword(Account account, String newPassword);
}
