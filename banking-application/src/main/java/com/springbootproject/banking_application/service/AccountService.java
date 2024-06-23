package com.springbootproject.banking_application.service;


import com.springbootproject.banking_application.dto.AccountDto;
import com.springbootproject.banking_application.dto.TransactionDto;
import com.springbootproject.banking_application.dto.TransferFundsDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id, double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long id);
    void transferFunds(TransferFundsDto transferFundsDto);
    List<TransactionDto> getAccountTransactions(Long accountId);
}
