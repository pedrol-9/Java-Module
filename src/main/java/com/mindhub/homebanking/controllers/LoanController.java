package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.LoanApplicationDTO;
import com.mindhub.homebanking.services.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@Transactional
public class LoanController {

  @Autowired
  private LoanService loanService;

  @GetMapping("/")
  public ResponseEntity<?> getLoans() {
    return loanService.getLoansAvailable();
  }

  @GetMapping("/current")
  public ResponseEntity<?> getLoansForAuthenticatedClient(Authentication authentication) {
    return loanService.getLoansForAuthenticatedClient(authentication);
  }

  @PostMapping("/")
  public ResponseEntity<?> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {
    return loanService.createLoanForAuthenticatedClient(authentication, loanApplicationDTO);
  }

}
