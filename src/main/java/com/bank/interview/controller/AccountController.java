package com.bank.interview.controller;

import com.bank.interview.dto.AccountRequest;
import com.bank.interview.dto.ApiResponse;
import com.bank.interview.dto.DepositRequest;
import com.bank.interview.dto.TransferRequest;
import com.bank.interview.model.Account;
import com.bank.interview.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Account>> createAccount(@Valid @RequestBody AccountRequest request) {
        Account account = accountService.createAccount(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully", account));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<Account>> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        return ResponseEntity
                .ok(ApiResponse.success("Account retrieved successfully", account));
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse<Account>> deposit(@PathVariable String accountNumber,
                                                        @Valid @RequestBody DepositRequest request) {
        Account account = accountService.deposit(accountNumber, request.getAmount());
        return ResponseEntity
                .ok(ApiResponse.success("Deposit successful", account));
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ApiResponse<Account>> withdraw(@PathVariable String accountNumber,
                                                         @Valid @RequestBody DepositRequest request) {
        Account account = accountService.withdraw(accountNumber, request.getAmount());
        return ResponseEntity
                .ok(ApiResponse.success("Withdrawal successful", account));
    }

    @PostMapping("/{accountNumber}/transfer")
    public ResponseEntity<ApiResponse<Void>> transfer(@PathVariable String accountNumber,
                                                      @Valid @RequestBody TransferRequest request) {
        accountService.transfer(accountNumber, request.getToAccountNumber(), request.getAmount());
        return ResponseEntity
                .ok(ApiResponse.success("Transfer successful", null));
    }
}
