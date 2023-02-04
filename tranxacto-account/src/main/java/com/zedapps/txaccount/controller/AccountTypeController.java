package com.zedapps.txaccount.controller;

import com.zedapps.txaccount.entity.AccountType;
import com.zedapps.txaccount.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author Shamah M Zoha
 * @since 28-Jan-23
 */

@RestController
public class AccountTypeController {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping(value = "/type/all")
    public List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
    }

    @GetMapping(value = "/type/{id}")
    public AccountType getAccountType(@PathVariable long id) {
        Optional<AccountType> accountType = accountTypeRepository.findById(id);

        if (accountType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found by id!");
        }

        return accountType.get();
    }

    @PostMapping(value = "/type/save")
    public AccountType saveAccountType(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @PostMapping(value = "/type/update/{id}")
    public AccountType updateType(AccountType accountType, @PathVariable long id) {
        Optional<AccountType> origAccountType = accountTypeRepository.findById(id);

        if (origAccountType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id passed for update!");
        }

        accountType.setId(origAccountType.get().getId());

        return accountTypeRepository.save(accountType);
    }

    @DeleteMapping(value = "/type/delete/{id}")
    public ResponseEntity<Object> deleteAccountType(@PathVariable long id) {
        Optional<AccountType> accountType = accountTypeRepository.findById(id);

        if (accountType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id passed for deletion!");
        }

        accountTypeRepository.delete(accountType.get());

        return ResponseEntity.ok("Successfully deleted!");
    }
}
