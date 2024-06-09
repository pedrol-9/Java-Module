package com.mindhub.homebanking.controllersTest;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

  @Autowired
  LoanRepository loanRepository;

  @Test
  public void existLoans(){
    List<Loan> loans = loanRepository.findAll();
    assertThat(loans,is(not(empty())));
  }

  @Test
  public void existPersonlLoan(){
    List<Loan> loans = loanRepository.findAll();
    assertThat(loans, hasItem(hasProperty("loanName", is("Personal"))));
  }
}
