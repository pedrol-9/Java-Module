package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTOs.NewTransactionDTO;
import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TransactionService {

  ResponseEntity<List<TransactionDTO>> getClientTransactions(Authentication authentication);

  ResponseEntity<String> makeTransaction(Authentication authentication, @RequestBody NewTransactionDTO newTransactionDTO);

  void saveTransaction(Transaction transaction);
}
