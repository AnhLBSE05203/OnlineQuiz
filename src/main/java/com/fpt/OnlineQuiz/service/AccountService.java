package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    void addAccount(Account account);

    /**
     * Find Account By Email
     * @param email
     * @return
     */
    Account findAccountByEmail(String email);

    /**
     * Find Account By Reset Password Token sent via Email
     * @param token
     * @return
     */
    Account findByResetPasswordToken(String token);

    /**
     * Update Reset Password Token to Account in DB
     * @param token
     * @param email
     */
    void updateResetPasswordToken(String token, String email);

    /**
     * Update Account Password
     * @param account User's Account
     * @param newPassword User's new Encoded Password
     */
    void updatePassword(Account account, String newPassword);
}
