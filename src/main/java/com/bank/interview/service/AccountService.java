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
        // TODO Part 2: create new Account, log the creation, and save it via repository
        return null;
    }

    public Account getAccount(String accountNumber) {
        // TODO Part 2: find account by accountNumber or throw AccountNotFoundException
        return null;
    }

    public Account deposit(String accountNumber, BigDecimal amount) {
        // TODO Part 2: validate amount > 0, find account, add amount, save and return
        return null;
    }

    public Account withdraw(String accountNumber, BigDecimal amount) {
        // TODO Part 2: validate amount > 0, find account, check balance, subtract amount, save and return
        return null;
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        // TODO Part 3 (optional/bonus): validate, withdraw from source, deposit to target
    }
}
