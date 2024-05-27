package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/clients")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/current/accounts")
    public ResponseEntity<?> getAccounts(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        List<Account> accountsList = accountRepository.findByClient(client);
        //List<AccountDto> accountsDtoList = accountsList.stream().map(AccountDto::new).collect(Collectors.toList());

        if (!accountsList.isEmpty()) {
            return new ResponseEntity<>(accountsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client has no accounts", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/current/create-account")
    public ResponseEntity<?> createAccountForAuthenticatedClient(Authentication authentication) {
        // Obtener el cliente actualmente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        // Verificar si el cliente ya tiene 3 cuentas
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Client already has 3 accounts", HttpStatus.FORBIDDEN);
        }

        // Crear una nueva cuenta para el cliente
        String accountNumber = Utils.generateAccountNumber();
        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>("Account created for authenticated client", HttpStatus.CREATED);
    }
}
