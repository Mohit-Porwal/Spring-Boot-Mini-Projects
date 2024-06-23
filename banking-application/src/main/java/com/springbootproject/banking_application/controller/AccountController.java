package com.springbootproject.banking_application.controller;

import com.springbootproject.banking_application.dto.AccountDto;
import com.springbootproject.banking_application.dto.TransactionDto;
import com.springbootproject.banking_application.dto.TransferFundsDto;
import com.springbootproject.banking_application.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    //Add account rest api
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get account rest api
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody HashMap<String, Double> request){
        AccountDto accountDto = accountService.deposit(id, request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody HashMap<String, Double> request){
        AccountDto accountDto = accountService.withdraw(id,request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }
    @PutMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferFundsDto transferFundsDto){
        accountService.transferFunds(transferFundsDto);
        return ResponseEntity.ok("Transfer successful");
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> fetchAccountTransactions(@PathVariable("id") Long accountId){

        List<TransactionDto> transactions = accountService.getAccountTransactions(accountId);

        return ResponseEntity.ok(transactions);
    }
}
