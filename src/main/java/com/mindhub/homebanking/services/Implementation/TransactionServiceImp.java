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
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private ClientRepository clientRepository;

  @Autowired
  private AccountRepository accountRepository;

  public ResponseEntity<List<TransactionDTO>> getClientTransactions(Authentication authentication) {
    //authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    Client client = clientRepository.findByEmail(username);

    if (client == null) {
      return ResponseEntity.notFound().build();
    }

    List<Transaction> transactions = transactionRespository.findByAccountClientId(client.getId());
    List<TransactionDTO> transactionDTOs = transactions.stream()
            .map(TransactionDTO::new)
            .collect(Collectors.toList());

    return ResponseEntity.ok(transactionDTOs);
  }


  @Override
  public ResponseEntity<String> makeTransaction(@RequestBody NewTransactionDTO newTransactionDTO) {
    // Obtiene el cliente autenticado
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    Client client = clientRepository.findByEmail(username);

    // Valida que el cliente esté autenticado
    if (client == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Client not authenticated");
    }

    // Valida que los datos de la solicitud no estén vacíos
    if (newTransactionDTO.amount() == 0 || newTransactionDTO.description().isBlank() ||
            newTransactionDTO.sourceAccountNumber().isEmpty() || newTransactionDTO.destinationAccountNumber().isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Empty fields");
    }

    // Valida que la cuenta de origen y destino sean diferentes
    if (newTransactionDTO.sourceAccountNumber().equals(newTransactionDTO.destinationAccountNumber())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Source account cannot be the same as destination account");
    }

    // Valida que el tipo de transacción sea un enum válido
    TransactionType type = newTransactionDTO.type();
    if (type != TransactionType.CREDIT && type != TransactionType.DEBIT) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid transaction type");
    }

    // Obtiene las cuentas de origen y destino
    Account sourceAccount = accountRepository.findByNumber(newTransactionDTO.sourceAccountNumber());
    Account destinationAccount = accountRepository.findByNumber(newTransactionDTO.destinationAccountNumber());

    // Valida que las cuentas existan
    if (sourceAccount == null || destinationAccount == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Source or destination account does not exist");
    }

    // Valida que la cuenta de origen pertenezca al cliente autenticado
    if (!sourceAccount.getClient().equals(client)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Source account does not belong to authenticated client");
    }

    // Valida que el cliente tenga suficientes fondos (en caso de ser una transacción de débito)
    if (type == TransactionType.DEBIT && sourceAccount.getBalance() < newTransactionDTO.amount()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient funds");
    }

    // Realiza la transacción
    Transaction transaction = new Transaction(type, newTransactionDTO.amount(),
            newTransactionDTO.description(), LocalDateTime.now());
    transaction.setAccount(sourceAccount);
    transactionRespository.save(transaction);

    // Actualiza el saldo de la cuenta de origen (en caso de ser una transacción de débito)
    if (type == TransactionType.DEBIT) {
      sourceAccount.setBalance(sourceAccount.getBalance() - newTransactionDTO.amount());
      accountRepository.save(sourceAccount);
    }

    // Realiza la transacción inversa (en caso de ser una transacción de débito)
    if (type == TransactionType.CREDIT) {
      Transaction reverseTransaction = new Transaction(type, newTransactionDTO.amount(),
              newTransactionDTO.description(), LocalDateTime.now());
      reverseTransaction.setAccount(destinationAccount);
      transactionRespository.save(reverseTransaction);
      destinationAccount.setBalance(destinationAccount.getBalance() + newTransactionDTO.amount());
      accountRepository.save(destinationAccount);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body("Transaction successful");
  }
}
