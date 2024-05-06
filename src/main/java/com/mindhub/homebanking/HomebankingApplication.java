package com.mindhub.homebanking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TrxRespository;
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
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TrxRespository trxRespository) {
		return (args) -> {
			System.out.println("Hola");

			// Client intances
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			Client leonel = new Client("Leonel", "Borjas", "leonel@borjas.com");
			Client matrona = new Client("Matrona", "Mandona", "matron@mandona.com");

			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);

			// Account instances
			Account account1 = new Account("VIN001", today, 5000.00);
			Account account2 = new Account("VIN002", tomorrow, 7500.00);
			Account account3 = new Account("VIN003", today, 2500.00);
			Account account4 = new Account("VIN004", today, 2900.00);
			Account account5 = new Account("VIN005", today, 9500.00);
			Account account6 = new Account("VIN006", tomorrow, 10800.00);
			Account account7 = new Account("VIN007", tomorrow, 20000.00);

			// Transaction instances
			Transaction trx1 = new Transaction(TransactionType.DEBIT, 120.52, "coffee_Walla", LocalDateTime.now());
			Transaction trx2 = new Transaction(TransactionType.CREDIT, 80.57, "store_purchase", LocalDateTime.now());
			Transaction trx3 = new Transaction(TransactionType.CREDIT, 20.87, "pharmacy_purchase", LocalDateTime.now());
			Transaction trx4 = new Transaction(TransactionType.CREDIT, 1080.33, "Mall_purchase", LocalDateTime.now());
			Transaction trx5 = new Transaction(TransactionType.CREDIT, 185.07, "gas_station", LocalDateTime.now());
			Transaction trx6 = new Transaction(TransactionType.CREDIT, 15.00, "onlyFans", LocalDateTime.now());
			Transaction trx7 = new Transaction(TransactionType.CREDIT, 14.20, "GooglePlay", LocalDateTime.now());
			Transaction trx8 = new Transaction(TransactionType.CREDIT, 18.20, "GooglePlay", LocalDateTime.now());

			// Assigner Method:
			melba.addAccount(account1);
			melba.addAccount(account2);
			leonel.addAccount(account3);
			leonel.addAccount(account4);
			leonel.addAccount(account5);
			matrona.addAccount(account6);
			matrona.addAccount(account7);
			account1.addTrx(trx1);
			account1.addTrx(trx8);
			account2.addTrx(trx2);
			account3.addTrx(trx3);
			account4.addTrx(trx4);
			account5.addTrx(trx5);
			account6.addTrx(trx6);
			account7.addTrx(trx7);

			clientRepository.save(melba);
			clientRepository.save(leonel);
			clientRepository.save(matrona);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);
			accountRepository.save(account5);
			accountRepository.save(account6);
			accountRepository.save(account7);
			trxRespository.save(trx1);
			trxRespository.save(trx2);
			trxRespository.save(trx3);
			trxRespository.save(trx4);
			trxRespository.save(trx5);
			trxRespository.save(trx6);
			trxRespository.save(trx7);
			trxRespository.save(trx8);

			System.out.println(melba);
			System.out.println(leonel);
			System.out.println(matrona);
		};
	}
}
