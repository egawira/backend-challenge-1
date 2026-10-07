package com.bank.interview.controller;

import com.bank.interview.dto.AccountRequest;
import com.bank.interview.dto.ApiResponse;
import com.bank.interview.dto.DepositRequest;
import com.bank.interview.dto.TransferRequest;
import com.bank.interview.model.Account;
import com.bank.interview.service.AccountService;
import jakarta.validation.Valid;
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
        // TODO Part 4: call service, return ApiResponse with HTTP 201
        return null;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<Account>> getAccount(@PathVariable String accountNumber) {
        // TODO Part 4: call service, return ApiResponse with HTTP 200
        return null;
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse<Account>> deposit(@PathVariable String accountNumber,
                                                        @Valid @RequestBody DepositRequest request) {
        // TODO Part 4: call service deposit, return ApiResponse
        return null;
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ApiResponse<Account>> withdraw(@PathVariable String accountNumber,
                                                         @Valid @RequestBody DepositRequest request) {
        // TODO Part 4: call service withdraw, return ApiResponse
        return null;
    }

    @PostMapping("/{accountNumber}/transfer")
    public ResponseEntity<ApiResponse<Void>> transfer(@PathVariable String accountNumber,
                                                      @Valid @RequestBody TransferRequest request) {
        // TODO Part 4 (optional/bonus): call service transfer, return empty success response
        return null;
    }
}
