package com.bank.interview.service;

import com.bank.interview.dto.AccountRequest;
import com.bank.interview.exception.AccountNotFoundException;
import com.bank.interview.exception.InsufficientBalanceException;
import com.bank.interview.model.Account;
import com.bank.interview.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount_shouldReturnSavedAccount() {
        // TODO Part 6 (bonus): write test for createAccount
    }

    @Test
    void getAccount_whenNotFound_shouldThrow() {
        // TODO Part 6 (bonus): verify AccountNotFoundException is thrown when findById returns empty
    }

    @Test
    void withdraw_whenInsufficientBalance_shouldThrow() {
        // TODO Part 6 (bonus): verify InsufficientBalanceException is thrown
    }
}
