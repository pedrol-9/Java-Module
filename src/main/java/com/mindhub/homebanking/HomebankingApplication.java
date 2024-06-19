package com.mindhub.homebanking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TransactionRespository transactionRespository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {

			System.out.println("Hola Mundo");

			 // Client instances
			 Client melba = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			 Client pedro = new Client("Pedro", "Sanabria", "pedro@sanabria.com", passwordEncoder.encode("123"));
			 //Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			 //Client pedro = new Client("Pedro", "Sanabria", "pedro@sanabria.com");

			 // Account instances
			 LocalDate today = LocalDate.now();
			 LocalDate tomorrow = today.plusDays(1);

			 Account account1 = new Account(today, 5000.00);
			 Account account2 = new Account(tomorrow, 7500.00);
			 Account account3 = new Account(today, 2500.00);
			 Account account4 = new Account(today, 2900.00);

			 // Transaction instances
			 Transaction trx1 = new Transaction(TransactionType.DEBIT, 120.52, "coffee_Walla", LocalDateTime.now());
			 Transaction trx2 = new Transaction(TransactionType.CREDIT, 80.57, "store_purchase", LocalDateTime.now());
			 Transaction trx3 = new Transaction(TransactionType.CREDIT, 20.87, "pharmacy_purchase", LocalDateTime.now());
			 Transaction trx4 = new Transaction(TransactionType.CREDIT, 1080.33, "Mall_purchase", LocalDateTime.now());
			 Transaction trx5 = new Transaction(TransactionType.CREDIT, 185.07, "gas_station", LocalDateTime.now());
			 Transaction trx6 = new Transaction(TransactionType.CREDIT, 15.00, "onlyFans", LocalDateTime.now());
			 Transaction trx7 = new Transaction(TransactionType.CREDIT, 14.20, "GooglePlay", LocalDateTime.now());
			 Transaction trx8 = new Transaction(TransactionType.CREDIT, 18.20, "GooglePlay", LocalDateTime.now());
			 Transaction trx9 = new Transaction(TransactionType.CREDIT, 180.21, "Domino's_Pizza", LocalDateTime.now());
			 Transaction trx10 = new Transaction(TransactionType.CREDIT, 500.00, "Tyba_APP", LocalDateTime.now());
			 Transaction trx11 = new Transaction(TransactionType.CREDIT, 10.00, "PrimeVideo", LocalDateTime.now());
			 Transaction trx12 = new Transaction(TransactionType.CREDIT, 12.00, "HBO_Max", LocalDateTime.now());

			// Loan Instances
			ArrayList<Integer> mortgagePayments = new ArrayList<>(List.of(12, 24, 36, 48, 60));
			ArrayList<Integer> personalPayments = new ArrayList<>(List.of(6, 12, 24));
			ArrayList<Integer> automotivePayments = new ArrayList<>(List.of(6, 12, 24, 36));

			 Loan mortgageLoan = new Loan("Mortgage", 500000, mortgagePayments);
			 Loan personalLoan = new Loan("Personal", 100000, personalPayments);
			 Loan automotiveLoan = new Loan("Automotive", 300000, automotivePayments);

			 //ClientLoan instances
			 ClientLoan clientLoan1 = new ClientLoan(400000.00, 60);
			 ClientLoan clientLoan2 = new ClientLoan(50000.00, 12);
			 ClientLoan clientLoan3 = new ClientLoan(100000.00, 24);
			 ClientLoan clientLoan4 = new ClientLoan(200000.00, 36);

			 // Card Instances
			 Card goldCard = new Card(melba, CardType.DEBIT, CardColor.GOLD, "8452-5896-6658-8558", 103, LocalDate.now().plusYears(5), LocalDate.now());
			 Card silverCard = new Card(melba, CardType.CREDIT, CardColor.SILVER, "4123-2589-9632-1478", 213, LocalDate.now().plusYears(5), LocalDate.now().minusDays(2));
			 Card platinumCard = new Card(pedro, CardType.CREDIT, CardColor.PLATINUM, "1120-0258-0058-1003", 521, LocalDate.now().plusYears(5), LocalDate.now().minusDays(4));

			 // Assigner Method:
			 melba.addAccount(account1);
			 melba.addAccount(account2);
			 pedro.addAccount(account3);
			 pedro.addAccount(account4);

			 account1.addTransaction(trx1);
			 account1.addTransaction(trx2);
			 account1.addTransaction(trx3);
			 account2.addTransaction(trx4);
			 account2.addTransaction(trx5);
			 account2.addTransaction(trx6);
			 account3.addTransaction(trx7);
			 account3.addTransaction(trx8);
			 account3.addTransaction(trx9);
			 account4.addTransaction(trx10);
			 account4.addTransaction(trx11);
			 account4.addTransaction(trx12);

			 mortgageLoan.addClientLoan(clientLoan1);
			 personalLoan.addClientLoan(clientLoan2);
			 automotiveLoan.addClientLoan(clientLoan3);
			 automotiveLoan.addClientLoan(clientLoan4);

			 melba.addClientLoan(clientLoan1);
			 melba.addClientLoan(clientLoan2);
			 pedro.addClientLoan(clientLoan3);
			 pedro.addClientLoan(clientLoan4);

			 melba.addCard(goldCard);
			 melba.addCard(silverCard);
			 pedro.addCard(platinumCard);

			 // Send objects to DB:
			 clientRepository.save(melba);
			 clientRepository.save(pedro);

			 accountRepository.save(account1);
			 accountRepository.save(account2);
			 accountRepository.save(account3);
			 accountRepository.save(account4);

			 transactionRespository.save(trx1);
			 transactionRespository.save(trx2);
			 transactionRespository.save(trx3);
			 transactionRespository.save(trx4);
			 transactionRespository.save(trx5);
			 transactionRespository.save(trx6);
			 transactionRespository.save(trx7);
			 transactionRespository.save(trx8);
			 transactionRespository.save(trx9);
			 transactionRespository.save(trx10);
			 transactionRespository.save(trx11);
			 transactionRespository.save(trx12);

			 loanRepository.save(mortgageLoan);
			 loanRepository.save(personalLoan);
			 loanRepository.save(automotiveLoan);

			 clientLoanRepository.save(clientLoan1);
			 clientLoanRepository.save(clientLoan2);
			 clientLoanRepository.save(clientLoan3);
			 clientLoanRepository.save(clientLoan4);

			 cardRepository.save(goldCard);
			 cardRepository.save(silverCard);
			 cardRepository.save(platinumCard);

			 cardRepository.save(goldCard);
			 cardRepository.save(silverCard);
			 cardRepository.save(platinumCard);

			 // printing objects in the console
			 System.out.println(melba);
			 System.out.println(pedro);

			 List<Loan> melbaLoan = melba.getLoans();
			 System.out.println(melbaLoan);

			 List<Client> mortgageLoanClients = mortgageLoan.getClients();
			 System.out.println(mortgageLoanClients);

		};
	}
}
