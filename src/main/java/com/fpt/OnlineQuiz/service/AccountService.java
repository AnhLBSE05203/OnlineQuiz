package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Token;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    void addAccount(Account account);

    /**
     * Find Account By Email
     * @param email User's email
     * @return
     */
    Account findAccountByEmail(String email);

    /**
     * Find Account By Reset Password Token sent via Email
     * @param token reset password token
     * @return
     */
    Account findByToken(String token, String tokenType);

    /**
     * Update Token to Account in DB
     * @param token confirm token
     * @param email user's email
     * @param tokenType token type
     */
    void addToken(String token, String email, String tokenType);

    /**
     * Update Account Password
     * @param account User's Account
     * @param newPassword User's new Encoded Password
     */
    void resetPassword(Account account, String newPassword, Token token);

    void updateAccount(Account account);
}
