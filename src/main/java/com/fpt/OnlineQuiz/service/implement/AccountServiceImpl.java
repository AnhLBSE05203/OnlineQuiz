package com.fpt.OnlineQuiz.service.implement;

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
		System.out.println("Found User: " + account);

		return account;
	}

	@Override
	public void updateResetPasswordToken(String token, String email) {
		Account account = accountRepository.findAccountByEmail(email);
		if (account != null) {
			account.setResetPasswordToken(token);
			Date date = new Date();
			account.setTokenCreatedTime(date);
			accountRepository.updateAccount(account);
		}
	}

	@Override
	public void updatePassword(Account account, String newPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(newPassword);
		account.setPassword(encodedPassword);

		account.setResetPasswordToken(null);
		account.setTokenCreatedTime(null);
		accountRepository.updateAccount(account);
	}
}
