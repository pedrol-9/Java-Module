package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin( origins = "*" )
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllAccounts(){
        List<AccountDTO> accountDTOs = accountRepository.findAll().stream().map(AccountDTO::new)
                .collect(toList());

        if (!accountDTOs.isEmpty()) {
        return new ResponseEntity<>(accountDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There're no accounts to display", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClients(@PathVariable Long id) {

        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }

        AccountDTO accountDTO = new AccountDTO(account);

        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
}
