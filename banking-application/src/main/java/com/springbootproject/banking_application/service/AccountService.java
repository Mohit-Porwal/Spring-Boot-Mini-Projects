package com.springbootproject.banking_application.service;


import com.springbootproject.banking_application.dto.AccountDto;
import com.springbootproject.banking_application.entity.Account;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

}
