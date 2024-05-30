//package com.mindhub.homebanking.controllers;
//
//import com.mindhub.homebanking.DTOs.ClientDTO;
//import com.mindhub.homebanking.DTOs.ClientLoanDTO;
//import com.mindhub.homebanking.DTOs.NewLoanDTO;
//import com.mindhub.homebanking.models.Client;
//import com.mindhub.homebanking.repositories.AccountRepository;
//import com.mindhub.homebanking.repositories.ClientRepository;
//import com.mindhub.homebanking.repositories.LoanRepository;
//import com.mindhub.homebanking.repositories.TransactionRespository;
//import com.mindhub.homebanking.services.LoanService;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureOrder;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@Transactional
//@RequestMapping("/api/clients")
//public class LoanController {
//
//  @Autowired
//  private ClientRepository clientRepository;
//
//  @Autowired
//  private TransactionRespository transactionRespository;
//
//  @Autowired
//  private AccountRepository accountRepository;
//
//  @Autowired
//  private LoanService loanService;
//
//  @GetMapping("/current/loans")
//  public ResponseEntity<?> getLoans(Authentication authentication) {
//    return loanService.getLoansByClient(authentication);
//  }
//
////  @PostMapping("/current/new-loan")
////  public ResponseEntity<?> createLoan(Authentication authentication, @RequestBody NewLoanDTO newLoanDTO) {
////    return loanService.createLoanForAuthenticatedClient(authentication, newLoanDTO);
////  }
//
//
//}
