package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountServiceImp implements AccountService {

  @Autowired
  public AccountRepository accountRepository;

  @Autowired
  public ClientRepository clientRepository;

  @Override
  public ResponseEntity<?> getAccounts(Authentication authentication) {
    Client client = clientRepository.findByEmail(authentication.getName());
    List<Account> accountsList = accountRepository.findByClient(client);
    List<AccountDTO> accountsDtoList = accountsList.stream()
            .map(AccountDTO::new)
            .toList();

    if (!accountsList.isEmpty()) {
      return new ResponseEntity<>(accountsDtoList, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Client has no accounts", HttpStatus.NOT_FOUND);
    }
  }

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
