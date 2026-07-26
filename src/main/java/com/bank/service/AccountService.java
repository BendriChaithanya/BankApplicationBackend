package com.bank.service;
import com.bank.UserEntity.User;
import com.bank.AccountEntity.Account;
import com.bank.TransactionEntity.Transaction;
import com.bank.UserEntity.User;
import com.bank.dto.AccountRequest;
import com.bank.dto.AccountResponse;
import com.bank.dto.TransferRequest;
import com.bank.enums.TransactionType;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    // CREATE ACCOUNT
    public AccountResponse createAccount(AccountRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = Account.builder()
                .accountNumber(request.getAccountNumber())
                .accountType(request.getAccountType())
                .balance(request.getBalance())
                .status(request.getStatus())
                .user(user)
                .build();

        Account saved = accountRepository.save(account);

        return mapToResponse(saved);
    }

    // GET ALL ACCOUNTS
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET ACCOUNT BY ID
    public AccountResponse getAccountById(Long id) {

        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToResponse(acc);
    }

    // UPDATE ACCOUNT
    public AccountResponse updateAccount(Long id, AccountRequest request) {

        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setAccountType(request.getAccountType());
        acc.setBalance(request.getBalance());

        Account updated = accountRepository.save(acc);

        return mapToResponse(updated);
    }

    // DELETE ACCOUNT
    public String deleteAccount(Long id) {

        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.delete(acc);

        return "Account Deleted Successfully";
    }

    // DEPOSIT MONEY
    @Transactional
    public AccountResponse deposit(Long accountId, double amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .date(LocalDateTime.now())
                .account(account)
                .build();

        transactionRepository.save(transaction);

        return mapToResponse(account);
    }

    // WITHDRAW MONEY
    @Transactional
    public AccountResponse withdraw(Long accountId, double amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(account.getBalance() - amount);

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.WITHDRAW)
                .date(LocalDateTime.now())
                .account(account)
                .build();

        transactionRepository.save(transaction);

        return mapToResponse(account);
    }

    // TRANSFER MONEY
    @Transactional
    public String transferMoney(TransferRequest request) {

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (fromAccount.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER)
                .date(LocalDateTime.now())
                .account(fromAccount)
                .build();

        transactionRepository.save(transaction);

        return "Transfer Successful";
    }
    // COMMON RESPONSE METHOD
    private AccountResponse mapToResponse(Account acc) {
        return new AccountResponse(
                acc.getId(),
                acc.getAccountNumber(),
                acc.getAccountType(),
                acc.getBalance(),
                acc.getStatus(),
                acc.getUser().getId()
        );
    }
    
    public List<AccountResponse> getAccountsByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}