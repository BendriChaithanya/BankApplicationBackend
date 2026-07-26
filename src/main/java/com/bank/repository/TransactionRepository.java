package com.bank.repository;

import com.bank.TransactionEntity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccountUserId(Long userId);

    List<Transaction> findByAccountUserEmail(String email);
}