package com.springbootproject.banking_application.dto;

import java.time.LocalDateTime;

public record TransactionDto(Long id, Long accountId, double amount, String transactionType, LocalDateTime timeStamp) {
}
