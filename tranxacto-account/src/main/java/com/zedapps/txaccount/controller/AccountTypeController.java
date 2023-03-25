package com.zedapps.txaccount.controller;

import com.zedapps.txaccount.entity.AccountType;
import com.zedapps.txaccount.repository.AccountTypeRepository;
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
        return getType(id);
    }

    @PostMapping(value = "/type/save")
    public AccountType saveAccountType(@Valid @RequestBody AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @PostMapping(value = "/type/update/{id}")
    public AccountType updateType(@Valid @RequestBody AccountType accountType, @PathVariable long id) {
        AccountType origAccountType = getType(id);

        accountType.setId(origAccountType.getId());

        return accountTypeRepository.save(accountType);
    }

    @DeleteMapping(value = "/type/delete/{id}")
    public ResponseEntity<Object> deleteAccountType(@PathVariable long id) {
        accountTypeRepository.delete(getType(id));

        return ResponseEntity.ok("Successfully deleted!");
    }

    private AccountType getType(long id) {
        Optional<AccountType> accountType = accountTypeRepository.findById(id);

        if (accountType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Type not found for id!");
        }

        return accountType.get();
    }
}
