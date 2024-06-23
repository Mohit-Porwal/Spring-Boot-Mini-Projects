package com.springbootproject.banking_application.dto;

public record TransferFundsDto(Long senderAccountId, Long receiverAccountId, double amount) {
}
