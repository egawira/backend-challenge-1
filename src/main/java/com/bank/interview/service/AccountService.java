package com.bank.interview.service;

import com.bank.interview.dto.AccountRequest;
import com.bank.interview.exception.AccountNotFoundException;
import com.bank.interview.exception.InsufficientBalanceException;
import com.bank.interview.model.Account;
import com.bank.interview.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(AccountRequest request) {
        BigDecimal initialBalance = request.getInitialBalance() != null
                ? request.getInitialBalance()
                : BigDecimal.ZERO;

        Account account = new Account(
                request.getAccountNumber(),
                request.getAccountHolderName(),
                initialBalance,
                request.getAccountType()
        );

        log.info("Creating account {} for holder {}", account.getAccountNumber(), account.getAccountHolderName());

        Account saved = accountRepository.save(account);
        log.info("Account {} created successfully with balance {}", saved.getAccountNumber(), saved.getBalance());
        return saved;
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    public Account deposit(String accountNumber, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        Account account = getAccount(accountNumber);
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);

        log.info("Deposited {} to account {}. New balance: {}", amount, accountNumber, newBalance);
        return accountRepository.save(account);
    }

    public Account withdraw(String accountNumber, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }

        Account account = getAccount(accountNumber);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(accountNumber);
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);

        log.info("Withdrew {} from account {}. New balance: {}", amount, accountNumber, newBalance);
        return accountRepository.save(account);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        if (fromAccountNumber != null && fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        withdraw(fromAccountNumber, amount);
        deposit(toAccountNumber, amount);

        log.info("Transferred {} from account {} to account {}", amount, fromAccountNumber, toAccountNumber);
    }
}
