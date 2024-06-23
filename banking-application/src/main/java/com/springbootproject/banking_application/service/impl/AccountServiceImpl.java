package com.springbootproject.banking_application.service.impl;

import com.springbootproject.banking_application.dto.AccountDto;
import com.springbootproject.banking_application.dto.TransactionDto;
import com.springbootproject.banking_application.dto.TransferFundsDto;
import com.springbootproject.banking_application.entity.Account;
import com.springbootproject.banking_application.entity.Transaction;
import com.springbootproject.banking_application.exception.AccountException;
import com.springbootproject.banking_application.mapper.AccountMapper;
import com.springbootproject.banking_application.repository.AccountRepository;
import com.springbootproject.banking_application.repository.TransactionRepository;
import com.springbootproject.banking_application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;
    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account does not exist") );
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account does not exist") );
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account does not exist") );

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient Balance");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account does not exist") );
        accountRepository.deleteById(id);
    }

    @Override
    public void transferFunds(TransferFundsDto transferFundsDto) {
        Account senderAccount = accountRepository.findById(transferFundsDto.senderAccountId()).orElseThrow(()-> new AccountException("Account does not exist") );
        Account receiverAccount = accountRepository.findById(transferFundsDto.receiverAccountId()).orElseThrow(()-> new AccountException("Account does not exist") );

        double senderAmount = transferFundsDto.amount();

        if(senderAmount > senderAccount.getBalance()){
            throw new RuntimeException("Insufficient Balance");
        }
        senderAccount.setBalance(senderAccount.getBalance() - senderAmount);
        receiverAccount.setBalance(receiverAccount.getBalance() + senderAmount);

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transaction transaction = new Transaction();
        transaction.setAccountId(transferFundsDto.senderAccountId());
        transaction.setAmount(transferFundsDto.amount());
        transaction.setTransactionType("TRANSFER");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long accountId) {

        List<Transaction> transactions = transactionRepository
                .findByAccountIdOrderByTimestampDesc(accountId);

        return transactions.stream()
                .map((transaction) -> convertEntityToDto(transaction))
                .collect(Collectors.toList());
    }

    private TransactionDto convertEntityToDto(Transaction transaction){
        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );
    }
}
