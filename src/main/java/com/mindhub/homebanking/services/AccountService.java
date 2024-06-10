package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {

  ResponseEntity<?> getAccounts(Authentication authentication);

  ResponseEntity<?> createAccountForAuthenticatedClient(Authentication authentication);

  List<AccountDTO> getAccountsByAuthenticatedClient(Client client);

  Account getAccountByNumber(String accountNumber);

  boolean existsByNumber(String accountNumber);

  void saveAccount(Account account);
}
