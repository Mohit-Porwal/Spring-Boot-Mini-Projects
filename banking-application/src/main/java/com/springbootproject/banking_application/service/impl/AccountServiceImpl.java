package com.springbootproject.banking_application.service.impl;

import com.springbootproject.banking_application.dto.AccountDto;
import com.springbootproject.banking_application.entity.Account;
import com.springbootproject.banking_application.mapper.AccountMapper;
import com.springbootproject.banking_application.repository.AccountRepository;
import com.springbootproject.banking_application.service.AccountService;
import org.springframework.stereotype.Service;

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
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account does not exist") );
        return AccountMapper.mapToAccountDto(account);
    }
}
