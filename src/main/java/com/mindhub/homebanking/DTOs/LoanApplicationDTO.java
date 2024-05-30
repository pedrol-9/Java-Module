package com.mindhub.homebanking.DTOs;

public record LoanApplicationDTO(long loanId, double amount, int payments, String destinationAccount) {
}

