package com.springbootproject.banking_application.service.impl;

import com.springbootproject.banking_application.dto.AccountDto;
import com.springbootproject.banking_application.dto.TransferFundsDto;
import com.springbootproject.banking_application.entity.Account;
import com.springbootproject.banking_application.exception.AccountException;
import com.springbootproject.banking_application.mapper.AccountMapper;
import com.springbootproject.banking_application.repository.AccountRepository;
import com.springbootproject.banking_application.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
    }
}
