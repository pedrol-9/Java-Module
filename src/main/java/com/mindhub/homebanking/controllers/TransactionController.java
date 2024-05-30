package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.NewTransactionDTO;
import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRespository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Transactional
public class TransactionController {

  @Autowired
  private TransactionRespository transactionRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionService transactionService;

  @GetMapping("/current/transactions")
  public ResponseEntity<List<TransactionDTO>> getClientTransactions(Authentication authentication) {
    return transactionService.getClientTransactions(authentication);
  }

  // Endpoint para realizar una transferencia
  @PostMapping("/current/make-transaction")
  public ResponseEntity<String> makeTransaction(Authentication authentication, @RequestBody NewTransactionDTO newTransactionDTO) {
    return transactionService.makeTransaction(authentication, newTransactionDTO);
  }
}