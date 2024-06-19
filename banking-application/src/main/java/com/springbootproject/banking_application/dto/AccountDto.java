package com.springbootproject.banking_application.dto;

import lombok.Data;

@Data
public class AccountDto {
    private long id;
    private String accountHolderName;
    private double balance;

    public AccountDto(long id, String accountHolderName, double balance) {

    }
}
