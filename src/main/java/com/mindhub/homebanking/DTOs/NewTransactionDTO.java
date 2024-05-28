package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.TransactionType;

public record NewTransactionDTO(String sourceAccountNumber, String destinationAccountNumber,
                                double amount, String description, TransactionType type) {}
