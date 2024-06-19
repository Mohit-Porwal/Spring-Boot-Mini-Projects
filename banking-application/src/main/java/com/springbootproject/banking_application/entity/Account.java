package com.springbootproject.banking_application.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Account_Holder_Name")
    private String accountHolderName;

    private double balance;

    public Account(long id, String accountHolderName, double balance) {
    }
}
