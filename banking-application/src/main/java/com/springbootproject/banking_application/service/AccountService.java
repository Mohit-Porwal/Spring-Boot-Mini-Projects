package com.springbootproject.banking_application.service;


import com.springbootproject.banking_application.dto.AccountDto;
import com.springbootproject.banking_application.entity.Account;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id, double amount);
}
