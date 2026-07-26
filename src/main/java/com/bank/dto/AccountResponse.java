package com.bank.dto;

import com.bank.enums.AccountStatus;
import com.bank.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private double balance;
    private AccountStatus status;
    private Long userId;

}