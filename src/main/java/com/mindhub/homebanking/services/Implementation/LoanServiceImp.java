package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.LoanApplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRespository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanServiceImp implements LoanService {

  @Autowired
  private LoanRepository loanRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRespository transactionRespository;

  @Override
  public ResponseEntity<?> getLoansAvailable(Authentication authentication) {
    String username = authentication.getName();
    Client client = clientRepository.findByEmail(username);

    List<LoanDTO> loans = loanRepository.findAll().stream().map(LoanDTO::new).toList();
    return new ResponseEntity<>(loans, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> createLoanForAuthenticatedClient(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {

    Client client = clientRepository.findByEmail(authentication.getName());
    String username = client.getEmail();

    // Valida que el monto de prestamo sea positivo
    if (loanApplicationDTO.amount() <= 0) {
      return new ResponseEntity<>("Please, check amount entry", HttpStatus.FORBIDDEN);
    }

    // Valida que el monto de prestamo sea
    if (loanApplicationDTO.payments() <= 0) {
      return new ResponseEntity<>("Please, check the entries and try again", HttpStatus.FORBIDDEN);
    }

    // valida si NO existe un prestamo igual a la cuota.
    if (!loanRepository.existsById(loanApplicationDTO.loanId())){
      return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
    }

    // Valida si el amount no excede el maxAmount del loan solicitado
    Loan loan = loanRepository.findById(loanApplicationDTO.loanId()).get();
    if (loanApplicationDTO.amount() > loan.getMaxAmount()) {
      return new ResponseEntity<>("Loan amount exceeds maximum amount", HttpStatus.FORBIDDEN);
    }

    // Valida si las cuotas están dentro de las opciones del loan solicitado
    List<Integer> payments = loan.getPayments();
    if (!payments.contains(loanApplicationDTO.payments())) {
      return new ResponseEntity<>("The selected installments are not within the current options for this loan", HttpStatus.FORBIDDEN);
    }

    // Valida que la cuenta de destino exista
    Account destinationAccount = accountRepository.findByNumber(loanApplicationDTO.destinationAccount());
    if (destinationAccount == null) {
      return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
    }

    // Valida que la cuenta de destino pertenezca al cliente autenticado
    if (!destinationAccount.getClient().getEmail().equals(username)) {
      return new ResponseEntity<>("Destination account does not belong to you.", HttpStatus.FORBIDDEN);
    }

    // Calcular la tasa de interés de acuerdo a las cuotas
    double interestRate;

    if (loanApplicationDTO.payments() < 12) {
      interestRate = 0.15;
    } else if (loanApplicationDTO.payments() == 12) {
      interestRate = 0.20;
    } else {
      interestRate = 0.25;
    }

    // Crear y configurar el nuevo préstamo con el monto y la tasa de interés
    double amountPlusInterest = loanApplicationDTO.amount() + (loanApplicationDTO.amount() * interestRate);
    ClientLoan newClientLoan = new ClientLoan(amountPlusInterest, loanApplicationDTO.payments());
    //newClientLoan.setClient(client);
    client.addClientLoan(newClientLoan);

    // Guardar entidades actualizadas
    clientRepository.save(client);

    // Crear y guardar la transacción
    String description = "New loan approved and credited";
    LocalDateTime date = LocalDateTime.now();
    Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.amount(), description, date);
    transaction.setAccount(destinationAccount);
    transactionRespository.save(transaction);

    // return new ResponseEntity<>("Loan created and credited to destination account", HttpStatus.CREATED);
    return new ResponseEntity<>("Loan created and credited to destination account", HttpStatus.CREATED);
  }
}