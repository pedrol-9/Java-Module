package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.DTOs.LoanApplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionService;
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
  private ClientService clientService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  @Override
  public ResponseEntity<?> getLoansAvailable() {
    List<LoanDTO> loans = loanRepository.findAll().stream().map(LoanDTO::new).toList();
    return new ResponseEntity<>(loans, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> createLoanForAuthenticatedClient(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {

    Client client = clientService.getActualClient(authentication);
    String username = client.getEmail();

    // Valida que el monto de préstamo sea positivo
    if (loanApplicationDTO.amount() <= 0) {
      return new ResponseEntity<>("Please, check amount entry", HttpStatus.FORBIDDEN);
    }

    // Valida que el numero de cuotas sea positivo
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
    // Account destinationAccount = accountRepository.findByNumber(loanApplicationDTO.destinationAccount());
    Account destinationAccount = accountService.getAccountByNumber(loanApplicationDTO.destinationAccount());
    if (destinationAccount == null) {
      return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
    }

    // Valida que la cuenta de destino pertenezca al cliente autenticado
    if (!destinationAccount.getClient().getEmail().equals(username)) {
      return new ResponseEntity<>("Destination account does not belong to you.", HttpStatus.FORBIDDEN);
    }

    // Calcular la tasa de interés de acuerdo a las cuotas
    double interestRate = calculateInterestRate(loanApplicationDTO.payments());

    // Crear y configurar el nuevo préstamo con el monto y la tasa de interés
    double amountPlusInterest = loanApplicationDTO.amount() + (loanApplicationDTO.amount() * interestRate);
    ClientLoan newClientLoan = new ClientLoan(amountPlusInterest, loanApplicationDTO.payments());
    newClientLoan.setClient(client); // es necesaria esta línea?
    client.addClientLoan(newClientLoan);

    // Guardar entidades actualizadas
    clientService.saveClient(client);

    // Crear y guardar la transacción
    String description = "New loan approved and credited";
    LocalDateTime date = LocalDateTime.now();
    Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.amount(), description, date);
    destinationAccount.addTransaction(transaction);
    transactionService.saveTransaction(transaction);
    destinationAccount.setBalance(destinationAccount.getBalance() + loanApplicationDTO.amount());
    accountService.saveAccount(destinationAccount);

    // return new ResponseEntity<>("Loan created and credited to destination account", HttpStatus.CREATED);
    return new ResponseEntity<>("Loan created and credited to destination account", HttpStatus.CREATED);
  }

  public double calculateInterestRate(int payments) {
    if (payments == 12) {
      return 0.20;
    } else if (payments > 12) {
      return 0.25;
    } else {
      return 0.15;
    }
  }

  @Override
  public void saveLoan(Loan loan) {
    loanRepository.save(loan);
  }
}

