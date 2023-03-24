package com.zedapps.txaccount.controller;

import com.zedapps.txaccount.entity.Account;
import com.zedapps.txaccount.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author Shamah M Zoha
 * @since 04-Feb-23
 */

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping("/active")
    public List<Account> getActiveAccounts() {
        return accountService.getActiveAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable long id) {
        Optional<Account> account = accountService.getAccount(id, true);

        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found by id!");
        }

        return account.get();
    }

    @PostMapping(value = "/save")
    public Account saveAccount(@Valid @RequestBody Account account) {
        return accountService.saveOrUpdateAccount(account);
    }

    @PostMapping(value = "/update/{id}")
    public Account updateAccount(@Valid @RequestBody Account account, @PathVariable long id) {
        Optional<Account> origAccount = accountService.getAccount(id, false);

        if (origAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id passed for update!");
        }

        account.setId(origAccount.get().getId());

        return accountService.saveOrUpdateAccount(account);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id) {
        Optional<Account> account = accountService.getAccount(id, false);

        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id passed for deletion!");
        }

        accountService.deleteAccount(account.get());

        return ResponseEntity.ok("Successfully deleted!");
    }
}
