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
    Account findByResetPasswordToken(String token);

    /**
     * Update Confirm Token to Account in DB
     * @param token confirm token
     * @param email user's email
     */
    void updateConfirmToken(String token, String email);

    /**
     * Find Account By Confirm Token sent via Email
     * @param token
     * @return
     */
    Account findByConfirmToken(String token);

    /**
     * Update Reset Password Token to Account in DB
     * @param token reset password token
     * @param email user's email
     */
    void updateResetPasswordToken(String token, String email);
    /**
     * Update Account Password
     * @param account User's Account
     * @param newPassword User's new Encoded Password
     */
    void updatePassword(Account account, String newPassword, Token token);
}
