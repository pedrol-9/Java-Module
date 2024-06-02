package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.LoanApplicationDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRespository;
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
  private ClientRepository clientRepository;

  @Autowired
  private TransactionRespository transactionRespository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private LoanRepository loanRepository;

  @Autowired
  private LoanService loanService;

  @GetMapping("/")
  public ResponseEntity<?> getLoans() {
    return loanService.getLoansAvailable();
  }

  @PostMapping("/loan-application")
  public ResponseEntity<?> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {
    return loanService.createLoanForAuthenticatedClient(authentication, loanApplicationDTO);
  }

}
