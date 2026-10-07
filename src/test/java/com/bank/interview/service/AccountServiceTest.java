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
        AccountRequest request = new AccountRequest();
        request.setAccountNumber("4444444444");
        request.setAccountHolderName("Diana Putri");
        request.setInitialBalance(new BigDecimal("75000"));
        request.setAccountType("SAVINGS");

        Account saved = new Account("4444444444", "Diana Putri", new BigDecimal("75000"), "SAVINGS");
        when(accountRepository.save(any(Account.class))).thenReturn(saved);

        Account result = accountService.createAccount(request);

        assertNotNull(result);
        assertEquals("4444444444", result.getAccountNumber());
        assertEquals("Diana Putri", result.getAccountHolderName());
        assertEquals(new BigDecimal("75000"), result.getBalance());
        assertEquals("SAVINGS", result.getAccountType());
    }

    @Test
    void getAccount_whenNotFound_shouldThrow() {
        when(accountRepository.findById("9999999999")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount("9999999999"));
    }

    @Test
    void withdraw_whenInsufficientBalance_shouldThrow() {
        Account account = new Account("2222222222", "Bob Santoso", new BigDecimal("50000"), "SAVINGS");
        when(accountRepository.findById("2222222222")).thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class,
                () -> accountService.withdraw("2222222222", new BigDecimal("100000")));
    }
}
