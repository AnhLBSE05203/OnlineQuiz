package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.dto.AccountAdminDTO;
import com.fpt.OnlineQuiz.dto.RegisterDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Role;
import com.fpt.OnlineQuiz.service.AccountService;
import com.fpt.OnlineQuiz.service.RoleService;
import com.fpt.OnlineQuiz.utils.Constants;
import com.fpt.OnlineQuiz.utils.Utils;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {

    private final AccountService accountService;
    private final RoleService roleService;

    public AdminAccountController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"", "/"})
    public String accountPage(Model model) {
        model.addAttribute("accountEditDTO", new AccountAdminDTO());
        model.addAttribute("accountAddDTO", new RegisterDTO());
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
    public String addAccount(@ModelAttribute("adminAddDTO") RegisterDTO accountAdminDTO) {
        Account account = new Account();
        Utils.copyNonNullProperties(accountAdminDTO, account);
        account = new Account();
        account.setEmail(accountAdminDTO.getEmail().toLowerCase());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(accountAdminDTO.getPassword());
        account.setPassword(encodedPassword);
        Date now = new Date();
        account.setCreatedTime(now);
        account.setUpdatedTime(now);
        account.setCreatedUserId(Constants.DEFAULT_CREATED_USER_ID);
        account.setUpdatedUserId(Constants.DEFAULT_UPDATED_USER_ID);
        account.setGender(accountAdminDTO.getGender());
        account.setPhone(accountAdminDTO.getPhone());
        account.setFullName(accountAdminDTO.getFullName());
        account.setStatus(Constants.STATUS_ACCOUNT_CONFIRMED);
        Role role = roleService.findRoleByName(Constants.ROLE_ADMIN);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        account.setRoles(roles);
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
        if (account.getGender() == 1) {
            accountAdminDTO.setGenderStr("Female");
        } else {
            accountAdminDTO.setGenderStr("Male");
        }
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
