package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTOs.LoanApplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {

  ResponseEntity<?> getLoansAvailable();

  ResponseEntity<?> createLoanForAuthenticatedClient(Authentication authentication, LoanApplicationDTO loanApplicationDTO);

  ResponseEntity<?> getLoansForAuthenticatedClient(Authentication authentication);

  void saveLoan(Loan loan);

}
