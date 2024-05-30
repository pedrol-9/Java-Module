//package com.mindhub.homebanking.services.Implementation;
//
//import com.mindhub.homebanking.DTOs.ClientLoanDTO;
//import com.mindhub.homebanking.DTOs.NewLoanDTO;
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.AccountRepository;
//import com.mindhub.homebanking.repositories.ClientRepository;
//import com.mindhub.homebanking.repositories.LoanRepository;
//import com.mindhub.homebanking.repositories.TransactionRespository;
//import com.mindhub.homebanking.services.LoanService;
//import com.mindhub.homebanking.services.TransactionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class LoanServiceImp implements LoanService {
//
//  @Autowired
//  private LoanRepository loanRepository;
//
//  @Autowired
//  private ClientRepository clientRepository;
//
//  @Autowired
//  private AccountRepository accountRepository;
//
//  @Autowired
//  private TransactionService transactionService;
//
//  @Autowired
//  private TransactionRespository transactionRespository;
//
//  @Autowired
//  private LoanService loanService;
//
//  @Autowired
//  private NewLoanDTO newLoanDTO;
//
//  @Override
//  public ResponseEntity<?> getLoansByClient(Authentication authentication) {
//
//    String username = authentication.getName();
//    Client client = clientRepository.findByEmail(username);
//    List<ClientLoanDTO> loans = client.getClientLoans().stream().map(ClientLoanDTO::new).toList();
//
//    return new ResponseEntity<>(loans, HttpStatus.OK);
//  }
//
//  @Override
//  public ResponseEntity<?> createLoanForAuthenticatedClient(Authentication authentication, NewLoanDTO newLoanDTO) {
//    Client client = clientRepository.findByEmail(authentication.getName());
//    String username = client.getEmail();
//
//    Account destinationAccount = accountRepository.findByNumber(newLoanDTO.destinationAccount());
//    Loan loan = loanRepository.findByLoanName(newLoanDTO.loanType());
//
//    if (newLoanDTO.amount() <= 0 || isValidPaymentOption(newLoanDTO.payments()) || newLoanDTO.payments() <= 0) {
//      return new ResponseEntity<>("Please, check the entries and try again", HttpStatus.FORBIDDEN);
//    }
//
//    // Cuenta de destino no existente
//    if (destinationAccount == null || !destinationAccount.getClient().getEmail().equals(username)) {
//      return new ResponseEntity<>("Destination account does not exist or does not belong to you.", HttpStatus.FORBIDDEN);
//    }
//
//    // New loan exceeds the maximum amount
//    if (newLoanDTO.amount() > loan.getMaxAmount()) {
//      return new ResponseEntity<>("Loan exceeds maximum amount", HttpStatus.FORBIDDEN);
//    }
//
//    // Crear y configurar el nuevo préstamo
//    client.addClientLoan(new ClientLoan(newLoanDTO.amount(), newLoanDTO.payments()));
//    loanRepository.save(loan);
//
//    String description = "New loan approved and credited";
//    LocalDateTime date = LocalDateTime.now();
//
//    transactionRespository.save(new Transaction(TransactionType.CREDIT, newLoanDTO.amount(), description, date));
//
//    return new ResponseEntity<>("Loan created and credited to destination account", HttpStatus.CREATED);
//  }
//
//  private boolean isValidPaymentOption(int payments) {
//    return !loanRepository.existsByPayments(newLoanDTO.payments());
//  }
//
//
//
//
//
//
//
////  public NewLoanDTO createLoan(Authentication authentication, NewLoanDTO newLoanDTO) throws LoanServiceException {
////    Client client = clientRepository.findByEmail(authentication.getName());
////    String username = client.getEmail();
////    validateNewLoan(username, newLoanDTO);
////    ClientLoan clientLoan = new ClientLoan(newLoanDTO.amount(), newLoanDTO.payments());
////    ClientLoanDTO clientLoanDTO = new ClientLoanDTO(clientLoan);
////    client.addClientLoan(clientLoan);
////    // Crear y configurar el nuevo préstamo
////
////    // Crear transacción de tipo CREDIT y asociar a la cuenta de destino
////    loanRepository.save(loan);
////    return new LoanDTO(loan);
////  }
////
////  private void validateNewLoan(String username, NewLoanDTO newLoanDTO) throws LoanServiceException {
////    if (newLoanDTO.amount() <= 0) {
////      throw new LoanServiceException("Amount must be greater than zero.");
////    }
////    if (!isValidPaymentOption(newLoanDTO.payments())) {
////      throw new LoanServiceException("Invalid payment option.");
////    }
////    Account destinationAccount = accountRepository.findByNumber(newLoanDTO.destinationAccount());
////    if (destinationAccount == null) {
////      throw new LoanServiceException("Destination account does not exist.");
////    }
////    if (!destinationAccount.getClient().getEmail().equals(username)) {
////      throw new LoanServiceException("Destination account does not belong to the authenticated client.");
////    }
////    if (newLoanDTO.amount() > getMaxAllowedAmount(newLoanDTO.loanType())) {
////      throw new LoanServiceException("Requested amount exceeds the maximum allowed amount for this loan type.");
////    }
////  }
////
////  private boolean isValidPaymentOption(int payments) {
////    // Validar que el número de pagos esté entre las opciones permitidas
////  }
////
////  private double getMaxAllowedAmount(String loanType) {
////    // Retornar el monto máximo permitido para el tipo de préstamo
////  }
////
////  private LoanDTO generateLoanSummary(NewLoanDTO newLoanDTO) {
////    double rate;
////    if (newLoanDTO.payments() == 12) {
////      rate = 0.20;
////    } else if (newLoanDTO.payments() > 12) {
////      rate = 0.25;
////    } else {
////      rate = 0.15;
////    }
////    double totalAmount = newLoanDTO.amount() + (newLoanDTO.amount() * rate);
////    return new LoanDTO(newLoanDTO.loanType(), totalAmount, newLoanDTO.payments(), newLoanDTO.destinationAccount());
////  }
////
////  private void createCreditTransaction(Account destinationAccount, double amount) {
////    Transaction transaction = new Transaction();
////    transaction.setType(TransactionType.CREDIT);
////    transaction.setAmount(amount);
////    transaction.setAccount(destinationAccount);
////    transactionService.save(transaction);
////  }
//}
