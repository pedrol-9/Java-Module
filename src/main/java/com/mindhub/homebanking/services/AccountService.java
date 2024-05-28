package com.mindhub.homebanking.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AccountService {

  ResponseEntity<?> getAccounts(Authentication authentication);

  ResponseEntity<?> createAccountForAuthenticatedClient(Authentication authentication);
}
