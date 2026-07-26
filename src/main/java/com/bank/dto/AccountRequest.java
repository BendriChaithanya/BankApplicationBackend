package com.bank.dto;

import com.bank.enums.AccountStatus;
import com.bank.enums.AccountType;
import lombok.Data;

@Data
public class AccountRequest {

    private String accountNumber;
    private AccountType accountType;
    private double balance;
    private AccountStatus status;
    private Long userId;

}
