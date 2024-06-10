package com.mindhub.homebanking.RepositoriesTest;

import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRespository;
import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {

  @Autowired
  private TransactionRespository transactionRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void shouldGetTransactionsByAccount() {
    Client client = new Client("Pedro", "Sanabria", "pedro@sanabria.com", "12345**");
    Account account = new Account(Utils.generateAccountNumber(), LocalDate.now(), 1000.0);
    client.addAccount(account);
    account.setClient(client);

    Transaction transaction = new Transaction(TransactionType.DEBIT, 2000, "withdrawal", LocalDateTime.now());
    Transaction transaction2 = new Transaction(TransactionType.DEBIT, 200, "Mall Purchase", LocalDateTime.now());
    account.addTransaction(transaction);
    transaction.setAccount(account);
    account.addTransaction(transaction2);
    transaction2.setAccount(account);

    clientRepository.save(client);
    accountRepository.save(account);
    transactionRepository.save(transaction);
    transactionRepository.save(transaction2);

    List<Transaction> transactions = transactionRepository.findByAccount(account);

    assertThat(transactions, containsInAnyOrder(transaction, transaction2));
  }

}
