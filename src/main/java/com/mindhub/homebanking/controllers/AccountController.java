package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/clients")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping("/current/accounts")
    public ResponseEntity<?> getAccountsForAuthenticatedClient(Authentication authentication) {
        return accountService.getAccounts(authentication);
    }

    @PostMapping("/current/accounts")
    public ResponseEntity<?> createAccountForAuthenticatedClient(Authentication authentication) {
        return accountService.createAccountForAuthenticatedClient(authentication);
    }
}
