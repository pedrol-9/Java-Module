package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.NewTransactionDTO;
import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRespository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImp implements TransactionService {

  @Autowired
  private TransactionRespository transactionRespository;

  @Autowired
  private AccountService accountService;

  @Autowired
  private ClientService clientService;

  @Override
  public ResponseEntity<List<TransactionDTO>> getClientTransactions(Authentication authentication) {

    String username = authentication.getName();
    Client client = clientService.getActualClient(authentication);

    List<Transaction> transactions = transactionRespository.findByAccountClientId(client.getId());

    List<TransactionDTO> transactionDTOs = transactions.stream()
            .map(TransactionDTO::new)
            .collect(Collectors.toList());

    return ResponseEntity.ok(transactionDTOs);
  }

  @Override
  public ResponseEntity<String> makeTransaction(Authentication authentication, @RequestBody NewTransactionDTO newTransactionDTO) {
    // Obtiene el cliente autenticado
    // authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    Client client = clientService.getActualClient(authentication);

    // Valida que los datos de la solicitud no estén vacíos
    if (newTransactionDTO.amount() == 0 || newTransactionDTO.description().isBlank() ||
            newTransactionDTO.sourceAccountNumber().isEmpty() || newTransactionDTO.destinationAccountNumber().isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Empty fields, try again");
    }

    // Valida que la cuenta de origen y destino sean diferentes
    if (newTransactionDTO.sourceAccountNumber().equals(newTransactionDTO.destinationAccountNumber())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Source account cannot be the same as destination account");
    }

    // Obtiene las cuentas de origen y destino
    Account sourceAccount = accountService.getAccountByNumber(newTransactionDTO.sourceAccountNumber());
    Account destinationAccount = accountService.getAccountByNumber(newTransactionDTO.destinationAccountNumber());

    // Valida que las cuentas existan
    if (sourceAccount == null || destinationAccount == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Source or destination account does not exist");
    }

    // Valida que la cuenta de origen pertenezca al cliente autenticado
    if (!sourceAccount.getClient().equals(client)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Source account does not belong to authenticated client");
    }

    // Valida que el cliente tenga suficientes fondos (en caso de ser una transacción de débito)
    if (sourceAccount.getBalance() < newTransactionDTO.amount()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient funds");
    }

    // Realiza la transacción
    Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -newTransactionDTO.amount(), newTransactionDTO.description(), LocalDateTime.now());
    sourceAccount.addTransaction(debitTransaction);
    transactionRespository.save(debitTransaction);
    // Actualiza el saldo de la cuenta de origen (en caso de ser una transacción de débito)
    sourceAccount.setBalance(sourceAccount.getBalance() - newTransactionDTO.amount());
    accountService.saveAccount(sourceAccount);

    // Realiza la transacción inversa (en caso de ser una transacción de débito)
    Transaction creditTransaction = new Transaction(TransactionType.CREDIT, newTransactionDTO.amount(), newTransactionDTO.description(), LocalDateTime.now());
    destinationAccount.addTransaction(creditTransaction);
    transactionRespository.save(creditTransaction);
    // Actualiza el saldo de la cuenta de origen (en caso de ser una transacción de débito)
    destinationAccount.setBalance(destinationAccount.getBalance() + newTransactionDTO.amount());
    accountService.saveAccount(destinationAccount);

    return ResponseEntity.status(HttpStatus.CREATED).body("Transaction successful");
  }

  @Override
  public void saveTransaction(Transaction transaction) {
    transactionRespository.save(transaction);
  }

}


