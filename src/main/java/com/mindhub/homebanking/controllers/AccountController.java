package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/clients")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private ClientService clientService;

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

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id, Authentication authentication) {
        // Llamar al servicio para obtener la cuenta por su ID
        Account account = accountService.getAccountById(id);
        AccountDTO accountDTO = new AccountDTO(account);

        // Verificar si la cuenta existe y si pertenece al cliente autenticado
        if (!account.getClient().getEmail().equals(authentication.getName())) {
            // Si la cuenta no existe o no pertenece al cliente autenticado, retornar respuesta con Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have access to this account");
        }

        // Si la cuenta existe y pertenece al cliente autenticado, retornar respuesta exitosa
        return ResponseEntity.ok(accountDTO);
    }
}
