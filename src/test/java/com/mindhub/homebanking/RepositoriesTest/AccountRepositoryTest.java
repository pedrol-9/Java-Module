package com.mindhub.homebanking.RepositoriesTest;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Test
  public void accountExistsInDatabase() {
    String accountNumber = Utils.generateAccountNumber();
    Account account = new Account(accountNumber, LocalDate.now(), 1000.0);
    accountRepository.save(account);

    // Comprobamos si la cuenta con el número generado existe en la base de datos
    boolean accountExists = accountRepository.existsByNumber(accountNumber);

    //Verificamos que la cuenta realmente exista
    assertThat(accountExists, is(true));
  }

  @Test
  public void canGetAccountByClient() {
    Client client = new Client("Pedro", "Sanabria", "pedro@sanabria.com", "12345**");
    Account account = new Account(Utils.generateAccountNumber(), LocalDate.now(), 1000.0);
    account.setClient(client);
    clientRepository.save(client);
    accountRepository.save(account);
    assertThat(accountRepository.findByClient(client), contains(account));
  }

}
