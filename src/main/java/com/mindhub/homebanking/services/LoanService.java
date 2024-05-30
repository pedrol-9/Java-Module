package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTOs.LoanApplicationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface LoanService {

  ResponseEntity<?> getLoansAvailable(Authentication authentication);

  ResponseEntity<?> createLoanForAuthenticatedClient(Authentication authentication, LoanApplicationDTO loanApplicationDTO);

}
