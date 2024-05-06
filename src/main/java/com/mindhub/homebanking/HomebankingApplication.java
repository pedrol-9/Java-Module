package com.mindhub.homebanking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TransactionRespository trxRespository) {
		return (args) -> {
			System.out.println("Hola");

			// Client intances
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			Client matrona = new Client("Matrona", "Mandona", "matron@mandona.com");

			// Account instances
			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);

			Account account1 = new Account("VIN001", today, 5000.00);
			Account account2 = new Account("VIN002", tomorrow, 7500.00);
			Account account3 = new Account("VIN003", today, 2500.00);
			Account account4 = new Account("VIN004", today, 2900.00);


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
			Transaction trx13 = new Transaction(TransactionType.CREDIT, 18.00, "DisneyPlus", LocalDateTime.now());
			Transaction trx14 = new Transaction(TransactionType.CREDIT, 18.20, "GooglePlay", LocalDateTime.now());
			Transaction trx15 = new Transaction(TransactionType.CREDIT, 152.25, "Ziru's_Pizza", LocalDateTime.now());
			Transaction trx16 = new Transaction(TransactionType.CREDIT, 75.08, "Cinemark_Col", LocalDateTime.now());

			// Assigner Method:
			melba.addAccount(account1);
			melba.addAccount(account2);
			matrona.addAccount(account3);
			matrona.addAccount(account4);
			account1.addTrx(trx1);
			account1.addTrx(trx2);
			account1.addTrx(trx3);
			account2.addTrx(trx4);
			account2.addTrx(trx5);
			account2.addTrx(trx6);
			account3.addTrx(trx7);
			account3.addTrx(trx8);
			account3.addTrx(trx9);
			account4.addTrx(trx10);
			account4.addTrx(trx11);
			account4.addTrx(trx12);

			clientRepository.save(melba);
			clientRepository.save(matrona);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);
			trxRespository.save(trx1);
			trxRespository.save(trx2);
			trxRespository.save(trx3);
			trxRespository.save(trx4);
			trxRespository.save(trx5);
			trxRespository.save(trx6);
			trxRespository.save(trx7);
			trxRespository.save(trx8);
			trxRespository.save(trx9);
			trxRespository.save(trx10);
			trxRespository.save(trx11);
			trxRespository.save(trx12);

			System.out.println(melba);
			System.out.println(matrona);
		};
	}
}
