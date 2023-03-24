package com.zedapps.txaccount.service;

import com.zedapps.common.dto.SupportingDocumentDto;
import com.zedapps.txaccount.entity.Account;
import com.zedapps.txaccount.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author Shamah M Zoha
 * @since 24-Mar-23
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SupportingDocumentService supportingDocumentService;

    public List<Account> getAccounts() {
        List<Account> accounts = accountRepository.findAll();

        setupSupportingDocuments(accounts);

        return accounts;
    }

    public List<Account> getActiveAccounts() {
        List<Account> activeAccounts = accountRepository.findAllActiveAccounts();

        setupSupportingDocuments(activeAccounts);

        return activeAccounts;
    }

    public Optional<Account> getAccount(long id, boolean isFetchDocument) {
        Optional<Account> account = accountRepository.findById(id);

        if (isFetchDocument) account.ifPresent(value -> setupSupportingDocuments(Collections.singletonList(value)));

        return account;
    }

    @Transactional
    public Account saveOrUpdateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Account account) {
        if (nonNull(account.getDocumentId())) supportingDocumentService.removeFile(account.getDocumentId());

        accountRepository.delete(account);
    }

    @Transactional
    public Account attachDocument(MultipartFile file, Account account) {
        if (nonNull(account.getDocumentId())) supportingDocumentService.removeFile(account.getDocumentId());

        SupportingDocumentDto documentInfo = supportingDocumentService.uploadDocument(file);

        account.setDocumentId(documentInfo.getId());
        account = saveOrUpdateAccount(account);

        account.setDocumentInfo(documentInfo);

        return account;
    }

    @Transactional
    public Account detachDocument(Account account) {
        supportingDocumentService.removeFile(account.getDocumentId());

        account.setDocumentId(null);
        account = saveOrUpdateAccount(account);

        return account;
    }

    private void setupSupportingDocuments(List<Account> accounts) {
        accounts.forEach(account -> {
            if (nonNull(account.getDocumentId())) {
                SupportingDocumentDto documentInfo = supportingDocumentService.getDocumentInfo(account.getDocumentId());
                account.setDocumentInfo(documentInfo);
            }
        });
    }
}
