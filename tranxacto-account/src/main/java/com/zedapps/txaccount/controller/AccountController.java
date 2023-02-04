package com.zedapps.txaccount.controller;

import com.zedapps.txaccount.entity.Account;
import com.zedapps.txaccount.repository.AccountRepository;
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
    private AccountRepository accountRepository;

    @GetMapping("/all")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/active")
    public List<Account> getActiveAccounts() {
        return accountRepository.findAllActiveAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found by id!");
        }

        return account.get();
    }

    @PostMapping(value = "/save")
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @PostMapping(value = "/update/{id}")
    public Account updateAccount(Account Account, @PathVariable long id) {
        Optional<Account> origAccount = accountRepository.findById(id);

        if (origAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id passed for update!");
        }

        Account.setId(origAccount.get().getId());

        return accountRepository.save(Account);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id passed for deletion!");
        }

        accountRepository.delete(account.get());

        return ResponseEntity.ok("Successfully deleted!");
    }
}
