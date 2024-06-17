package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImp implements AccountService {

  @Autowired
  public AccountRepository accountRepository;

  @Autowired
  public ClientRepository clientRepository;

  @Autowired
  public ClientService clientService;

  @Override
  public ResponseEntity<?> getAccounts(Authentication authentication) {
    Client client = clientService.getActualClient(authentication);
    List<AccountDTO> accountsDtoList = getAccountsByAuthenticatedClient(client);

    if (!accountsDtoList.isEmpty()) {
      return new ResponseEntity<>(accountsDtoList, HttpStatus.OK);
    }

    return new ResponseEntity<>("You have no accounts", HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<?> createAccountForAuthenticatedClient(Authentication authentication) {
    // Obtener el cliente actualmente autenticado
    Client client = clientService.getActualClient(authentication);

    // Verificar si el cliente ya tiene 3 cuentas
    if (client.getAccounts().size() >= 3) {
      return new ResponseEntity<>("You already has 3 accounts", HttpStatus.FORBIDDEN);
    }

    // Crear una nueva cuenta para el cliente
    String accountNumber = Utils.generateAccountNumber();
    Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0);
    client.addAccount(newAccount);
    saveAccount(newAccount);

    return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
  }

  @Override
  public List<AccountDTO> getAccountsByAuthenticatedClient(Client client) {
    return accountRepository.findByClient(client).stream()
            .map(AccountDTO::new)
            .collect(toList());
  }

  @Override
  public Account getAccountByNumber(String number) {
    return accountRepository.findByNumber(number);
  }

  @Override
  public Account getAccountById(Long id) {
    // Implementar lógica para buscar la cuenta por su ID en el repositorio
    return accountRepository.findById(id).orElse(null);
  }

  @Override
  public void saveAccount(Account account) {
    accountRepository.save(account);
  }

  @Override
  public boolean existsByNumber(String number) {
    return accountRepository.existsByNumber(number);
  }
}
