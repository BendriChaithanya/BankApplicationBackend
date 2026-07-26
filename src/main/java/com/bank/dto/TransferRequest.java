package com.bank.dto;

import lombok.Data;

@Data
public class TransferRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private double amount;

}