package com.bank.TransactionEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.bank.AccountEntity.Account;
import com.bank.enums.TransactionType;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(
            name = "transaction_seq",
            sequenceName = "transaction_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "transaction_date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType type;

    @Column(nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}