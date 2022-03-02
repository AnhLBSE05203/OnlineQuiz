package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.AccountAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {

    private final AccountService accountService;

    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public String accountPage() {
        return "admin_account_page";
    }

    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<AccountAdminDTO> getAllAccountAdmin(@RequestBody PagingRequest pagingRequest) {
        Page<AccountAdminDTO> listAccount = accountService.listAccountAdmin(pagingRequest);
        return listAccount;
    }

    @GetMapping("/{id}")
    public Account detailAccountPage(@PathVariable Integer id) {
        Account account = accountService.detailAccount(id);
        return account;
    }


}
