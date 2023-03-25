package com.zedapps.txaccount.controller;

import com.zedapps.common.dto.SupportingDocumentDto;
import com.zedapps.txaccount.entity.Account;
import com.zedapps.txaccount.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
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
        return getAccount(id, true);
    }

    @PostMapping(value = "/save")
    public Account saveAccount(@Valid @RequestBody Account account) {
        return accountService.saveOrUpdateAccount(account);
    }

    @PostMapping(value = "/update/{id}")
    public Account updateAccount(@Valid @RequestBody Account account, @PathVariable long id) {
        Account origAccount = getAccount(id, false);

        account.setId(origAccount.getId());

        return accountService.saveOrUpdateAccount(account);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id) {
        accountService.deleteAccount(getAccount(id, false));

        return ResponseEntity.ok("Successfully deleted!");
    }

    @PostMapping(value = "/attachDoc/{id}")
    public Account attachDocument(@RequestPart MultipartFile document, @PathVariable long id) {
        return accountService.attachDocument(document, getAccount(id, false));
    }

    @GetMapping(value = "/getDoc/{id}")
    public ResponseEntity<?> getAccountDocument(@PathVariable long id) {
        Account account = getAccount(id, true);

        if (Objects.isNull(account.getDocumentId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account does not have document!");
        }

        byte[] documentContent = accountService.getAccountDocument(account);
        SupportingDocumentDto documentInfo = account.getDocumentInfo();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(documentInfo.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + documentInfo.getName())
                .body(documentContent);
    }

    @DeleteMapping(value = "/detachDoc/{id}")
    public Account detachDocument(@PathVariable long id) {
        Account account = getAccount(id, false);

        if (Objects.isNull(account.getDocumentId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account does not have document attached!");
        }

        return accountService.detachDocument(account);
    }

    private Account getAccount(long id, boolean isFetchDocument) {
        Optional<Account> account = accountService.getAccount(id, isFetchDocument);

        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found for id!");
        }

        return account.get();
    }
}
