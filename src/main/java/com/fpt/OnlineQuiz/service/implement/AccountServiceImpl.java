package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.TokenRepository;
import com.fpt.OnlineQuiz.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fpt.OnlineQuiz.dao.AccountRepository;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.service.AccountService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TokenRepository tokenRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findAccountByEmail(username);

		if (account == null) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

		System.out.println("Found User: " + account);

		return account;
	}

	public void addAccount(Account account) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(account.getPassword());
		account.setPassword(encodedPassword);
		accountRepository.addAccount(account);
	}

	public Account findAccountByEmail(String email) {

		return accountRepository.findAccountByEmail(email);
	}

	@Override
	public Account findByResetPasswordToken(String token) {
		Account account = accountRepository.findByResetPasswordToken(token);

		return account;
	}

	@Override
	public void updateConfirmToken(String tokenString, String email) {
		Account account = accountRepository.findAccountByEmail(email);
		if (account != null) {
			Token token = new Token();
			token.setTokenString(tokenString);
			token.setAccount(account);
			Date date = new Date();
			token.setCreatedDate(date);
			token.setTokenType("TOKEN_CONFIRM");
			tokenRepository.addToken(token);
		}
	}

	@Override
	public Account findByConfirmToken(String token) {
		Account account = accountRepository.findByConfirmToken(token);
		return account;
	}

	@Override
	public void updateResetPasswordToken(String tokenString, String email) {
		Account account = accountRepository.findAccountByEmail(email);
		if (account != null) {
			Token token = new Token();
			token.setTokenString(tokenString);
			token.setAccount(account);
			Date date = new Date();
			token.setCreatedDate(date);
			token.setTokenType("TOKEN_RESET_PASSWORD");
			tokenRepository.addToken(token);
		}
	}

	@Override
	public void updatePassword(Account account, String newPassword, Token token) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(newPassword);
		account.setPassword(encodedPassword);
		accountRepository.updateAccount(account);
		tokenRepository.deleteToken(token);
	}
}
