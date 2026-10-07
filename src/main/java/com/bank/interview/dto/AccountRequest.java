package com.bank.interview.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AccountRequest {

    @NotBlank(message = "Account number must not be blank")
    @Size(min = 10, max = 20, message = "Account number must be between 10 and 20 characters")
    private String accountNumber;

    @NotBlank(message = "Account holder name must not be blank")
    @Size(min = 3, max = 100, message = "Account holder name must be between 3 and 100 characters")
    private String accountHolderName;

    @NotNull(message = "Initial balance must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Initial balance must be greater than or equal to 0")
    private BigDecimal initialBalance;

    @NotBlank(message = "Account type must not be blank")
    private String accountType;

    public AccountRequest() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
