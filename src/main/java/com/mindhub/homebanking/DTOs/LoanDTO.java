package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {

  private String loanName;

  private double maxAmount;

  private List<Integer> payments;

  public LoanDTO(Loan loan) {
    this.loanName = loan.getLoanName();
    this.maxAmount = loan.getMaxAmount();
    this.payments = loan.getPayments();
  }

  public String getLoanName() {
    return loanName;
  }

  public double getMaxAmount() {
    return maxAmount;
  }

  public List<Integer> getPayments() {
    return payments;
  }
}
