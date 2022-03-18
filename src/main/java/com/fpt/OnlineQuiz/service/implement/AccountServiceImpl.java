package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.AccountRepository;
import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDAccountRepository;
import com.fpt.OnlineQuiz.dao.TokenRepository;
import com.fpt.OnlineQuiz.dto.AccountAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Token;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private CRUDAccountRepository accountCRUDRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByEmail(username);

        if (account == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        return account;
    }

    public void addAccount(Account account) {
        accountRepository.addAccount(account);
    }

    public Account findAccountByEmail(String email) {

        return accountRepository.findAccountByEmail(email);
    }

    @Override
    public Account findByToken(String token, String tokenType) {
        Account account = accountRepository.findByToken(token, tokenType);

        return account;
    }

    @Override
    public void addToken(String tokenString, String email, String tokenType) {
        Account account = accountRepository.findAccountByEmail(email);
        if (account != null) {
            Token token = new Token();
            token.setTokenString(tokenString);
            token.setAccount(account);
            Date date = new Date();
            token.setCreatedDate(date);
            token.setTokenType(tokenType);
            tokenRepository.addToken(token);
        }
    }

    @Override
    public void resetPassword(Account account, String newPassword, Token token) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        account.setPassword(encodedPassword);
        Date now = new Date();
        account.setUpdatedTime(now);
        account.setUpdatedUserId(1);
        accountRepository.updateAccount(account);
        tokenRepository.deleteToken(token);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.updateAccount(account);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        accountRepository.updateAccount(account);
    }

    @Override
    public Page<AccountAdminDTO> listAccountAdmin(PagingRequest pagingRequest) {
        List<Account> listAccount = accountRepository.getAllAccountAdmin(pagingRequest);
        long count = accountRepository.getAllAccountAdminCountTotalRecord(pagingRequest);
        List<AccountAdminDTO> listAccountAdminDTO = new ArrayList<>();
        for(Account account: listAccount) {
            AccountAdminDTO accountAdminDTO = new AccountAdminDTO();
            Utils.copyNonNullProperties(account, accountAdminDTO);
            listAccountAdminDTO.add(accountAdminDTO);
        }
        Page<AccountAdminDTO> page = new Page<>(listAccountAdminDTO);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());
        return page;
    }

    @Override
    public Account detailAccount(Integer id) {
        return accountCRUDRepository.getAccountById(id);
    }

}
