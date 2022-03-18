package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.AccountAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.utils.Constants;
import com.fpt.OnlineQuiz.utils.Utils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {

    private final AccountService accountService;

    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = {"", "/"})
    public String accountPage(Model model) {
        model.addAttribute("accountEditDTO", new AccountAdminDTO());
        model.addAttribute("accountAddDTO", new AccountAdminDTO());
        return "admin_account_page";
    }

    @PostMapping(value = "/edit")
    public String editAccount(@ModelAttribute("adminEditDTO") AccountAdminDTO accountAdminDTO) {
        Account account = accountService.detailAccount(accountAdminDTO.getId());
        Utils.copyNonNullProperties(accountAdminDTO, account);
        accountService.updateAccount(account);
        return Constants.LINK_REDIRECT + "/admin/account";
    }

    @PostMapping(value = "/add")
    public String addAccount(@ModelAttribute("adminAddDTO") AccountAdminDTO accountAdminDTO) {
        Account account = new Account();
        Utils.copyNonNullProperties(accountAdminDTO, account);
        accountService.addAccount(account);
        return Constants.LINK_REDIRECT + "/admin/account";
    }

    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<AccountAdminDTO> getAllAccountAdmin(@RequestBody PagingRequest pagingRequest) {
        Page<AccountAdminDTO> listAccount = accountService.listAccountAdmin(pagingRequest);
        return listAccount;
    }

    @GetMapping(value = "/view/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountAdminDTO detailAccount(@PathVariable Integer id) {
        Account account = accountService.detailAccount(id);
        AccountAdminDTO accountAdminDTO = new AccountAdminDTO();
        Utils.copyNonNullProperties(account, accountAdminDTO);
        return accountAdminDTO;
    }

    @GetMapping("/{id}")
    public Account detailAccountPage(@PathVariable Integer id) {
        Account account = accountService.detailAccount(id);
        return account;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteAccount(@PathVariable Integer id) {
        Account account = accountService.detailAccount(id);
        account.setStatus(Constants.STATUS_DELETED);
        accountService.updateAccount(account);
        return Constants.LINK_REDIRECT + "/admin/account";
    }


}
