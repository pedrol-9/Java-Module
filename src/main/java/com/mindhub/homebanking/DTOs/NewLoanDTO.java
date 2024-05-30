package com.mindhub.homebanking.DTOs;

public record NewLoanDTO(String loanType, double amount, int payments, String destinationAccount) {
}

