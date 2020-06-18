package com.xplore.casestudy.bankapplication.services;

import com.xplore.casestudy.bankapplication.models.Account;
import com.xplore.casestudy.bankapplication.models.AccountStatus;
import com.xplore.casestudy.bankapplication.models.Transaction;
import com.xplore.casestudy.bankapplication.repositories.AccountRepository;
import com.xplore.casestudy.bankapplication.repositories.AccountStatusRepository;
import com.xplore.casestudy.bankapplication.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, AccountStatusRepository accountStatusRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.accountStatusRepository = accountStatusRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account getAccountByCustomerId(String id) {
        return this.accountRepository.getFirstByCustomerId(Long.parseLong(id))
                .orElseThrow(() -> {
                    throw new RuntimeException();
                });
    }

    public void createAccounts(Account account) {
        account.setCreationDate(LocalDate.now());
        account.setLastTransactionDate(LocalDate.now());
        account.setAccountDuration(1L);

        final Account save = this.accountRepository.save(account);
        String type = "S".equals(save.getAccountType()) ? "Savings" : "Current";
        AccountStatus status = new AccountStatus(save.getAccountId(), save.getCustomerId(),
                type, "Active", "Account created successfully!", LocalDateTime.now());
        this.accountStatusRepository.save(status);
    }

    public void deleteAccountById(String id) {
        this.accountRepository.deleteById(Long.parseLong(id));
        this.accountStatusRepository.deleteById(Long.parseLong(id));
    }

    public List<Long> getAccountIds() {
        return this.accountRepository.getAllAccountId();
    }

    public List<AccountStatus> getAllAccountStatus() {
        return this.accountStatusRepository.findAll();
    }

    public AccountStatus saveAccountStatus(AccountStatus accountStatus) {
        return this.accountStatusRepository.save(accountStatus);
    }

    public Account getAccountById(String id) {
        return this.accountRepository.getOne(Long.parseLong(id));
    }

    //transactional
    public void depositMoney(Account account, int amount) {
        Transaction transaction = new Transaction(null, account.getAccountType(),
                amount, LocalDate.now(), "DEPOSIT", "SELF", "SELF",
                account.getAccountId(), null);
        transactionRepository.save(transaction);
        this.accountRepository.updateMoneyAfterTransaction(account.getAccountId(),
                account.getAccountBalance() + amount, LocalDate.now());
    }

    public void withdrawMoney(Account account, int amount) {

        Transaction transaction = new Transaction(null, account.getAccountType(),
                amount, LocalDate.now(), "WITHDRAW", "SELF", "SELF",
                account.getAccountId(), null);
        transactionRepository.save(transaction);
        this.accountRepository.updateMoneyAfterTransaction(account.getAccountId(),
                account.getAccountBalance() - amount, LocalDate.now());
    }

    public List<Transaction> getNTransactionById(String id) {
        return transactionRepository
                .getTop10BySourceAccountIdOrderByTransactionDateDesc(Long.parseLong(id));
    }

    public List<Transaction> getAllBetweenStartAndEnd(LocalDate start, LocalDate end) {
        return this.transactionRepository.getAllBetweenStartAndEnd(start, end);
    }

}
